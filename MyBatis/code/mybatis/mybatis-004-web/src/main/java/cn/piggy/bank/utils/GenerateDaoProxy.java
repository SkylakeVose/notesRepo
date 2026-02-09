package cn.piggy.bank.utils;

import org.apache.ibatis.javassist.CannotCompileException;
import org.apache.ibatis.javassist.ClassPool;
import org.apache.ibatis.javassist.CtClass;
import org.apache.ibatis.javassist.CtMethod;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.session.SqlSession;

import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * 工具类：可以动态生成DAO的实现类（可以动态生成DAO的代理类）
 */
public class GenerateDaoProxy {

    /**
     * 生成DAO接口实现类，并且将实现类的对象创建并返回。
     * @param daoInterface dao接口
     * @return dao接口实现类的实例化对象
     */
    public static Object generate(SqlSession sqlSession, Class daoInterface) {
        // 类池
        ClassPool pool = ClassPool.getDefault();
        // 制造类（cn.piggy.bank.dao.AccountDao -> cn.piggy.bank.dao.AccountDaoProxy）
        CtClass ctClass = pool.makeClass(daoInterface.getName() + "Proxy"); // 本质上就是在内存中动态生成要给代理类
        // 制造接口
        CtClass ctInterface = pool.makeInterface(daoInterface.getName());
        // 实现接口
        ctClass.addInterface(ctInterface);
        // 实现接口中所有方法
        Method[] methods = daoInterface.getDeclaredMethods();
        Arrays.stream(methods).forEach(method -> {
            // method是接口中的抽象方法
            // 将method这个抽象方法进行实现
            try {
                // Account selectByActno(String actno);
                // public  Account selectByActno(String actno) {代码}
                StringBuilder methodCode = new StringBuilder();
                methodCode.append("public ");
                methodCode.append(method.getReturnType().getName());
                methodCode.append(" ");
                methodCode.append(method.getName());
                methodCode.append("(");
                // 需要方法的形参列表
                Class<?>[] parameterTypes = method.getParameterTypes();
                for (int i = 0; i < parameterTypes.length; i++) {
                    Class<?> parameterType = parameterTypes[i];
                    methodCode.append(parameterType.getName());
                    methodCode.append(" ");
                    methodCode.append("arg" + i);
                    if (i!=parameterTypes.length) {
                        methodCode.append(",");
                    }
                }
                methodCode.append(")");
                methodCode.append("{");
                // 需要方法体中的代码
                methodCode.append("org.apache.ibatis.session.SqlSession sqlSession = org.apache.ibatis.session.SqlSessionUtil.openSession();");
                // 需要知道是什么类型的SQL语句
                /**
                 * sql语句的id是框架使用这提供的，具有多变性。对于框架开发者来说，这个是没办法知道的。
                 * 因此为了能正常获取到使用者编写的SQL语句细节，开发者对框架进行一个规定：
                 * 凡是使用GenerateDaoProxy机制的，sqlId不能随便写：namespace必须是dao接口的全限定名，id必须是dao接口的方法名。
                 */
                String sqlId = daoInterface.getName() + "." + method.getName();
                SqlCommandType sqlCommandType = sqlSession.getConfiguration().getMappedStatement().getSqlCommandType();
                switch(sqlCommandType) {
                    case INSERT:
                        break;
                    case DELETE:
                        break;
                    case UPDATE:
                        methodCode.append("return sqlSession.update(\"" + sqlId +"\", arg0);");
                        break;
                    case SELECT:
                        methodCode.append("return sqlSession.selectOne(\"" + sqlId + "\", arg0);");
                        break;
                    default:
                        break;
                }

                methodCode.append("}");

                CtMethod ctMethod = CtMethod.make("", ctClass);
                ctClass.addMethod(ctMethod);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        // 创建对象
        Object obj = null;
        try {
            Class<?> clazz = ctClass.toClass();
            obj = clazz.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return obj;
    }

}
