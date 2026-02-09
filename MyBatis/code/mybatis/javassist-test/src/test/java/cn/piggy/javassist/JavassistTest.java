package cn.piggy.javassist;

import cn.piggy.bank.dao.AccountDao;
import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import org.junit.Test;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Arrays;

public class JavassistTest {

    @Test
    public void testGenerateAccountDaoImpl() throws Exception {
        // 制造类池
        ClassPool pool = ClassPool.getDefault();
        // 制造类
        CtClass ctClass = pool.makeClass("cn.piggy.bank.dao.impl.AccountDaoImpl");
        // 制造接口
        CtClass ctInterface = pool.makeInterface("cn.piggy.bank.dao.AccountDao");
        // 实现接口
        ctClass.addInterface(ctInterface);
        // 实现接口中所有的方法
        // 获取接口中所有方法
        Method[] methods = AccountDao.class.getDeclaredMethods();
        Arrays.stream(methods).forEach(method -> {
            // method是接口中的抽象方法
            // 把method抽象方法给实现了
            try{
                // public void delete() {}
                // public int update(String actno, Double balance) {}
                StringBuilder methodCode = new StringBuilder();
                methodCode.append("public ");   // 追加修饰符列表
                methodCode.append(method.getReturnType().getName());    // 追加返回值类型
                methodCode.append(" ");
                methodCode.append(method.getName());    // 追加方法名
                methodCode.append("(");
                // 拼接参数 String actno, Double balance
                Class<?>[] parameterTypes = method.getParameterTypes();
                for (int i = 0; i < parameterTypes.length; i++) {
                    Class<?> parameterType = parameterTypes[i];
                    methodCode.append(parameterType.getName());
                    methodCode.append(" ");
                    methodCode.append("arg" + i);
                    if(i != parameterTypes.length - 1){
                        methodCode.append(",");
                    }
                }
                methodCode.append("){System.out.println(11111);");
                // 动态添加return语句
                String returnTypeSimpleName = method.getReturnType().getSimpleName();
                if ("void".equals(returnTypeSimpleName)) {

                } else if("int".equals(returnTypeSimpleName)){
                    methodCode.append("return 1;");
                } else if("String".equals(returnTypeSimpleName)){
                    methodCode.append("return \"hello\";");
                }
                methodCode.append("}");
                System.out.println(methodCode);
                CtMethod ctMethod = CtMethod.make(methodCode.toString(), ctClass);
                ctClass.addMethod(ctMethod);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        // 在内存中生成class,并且加载到JVM当中
        Class<?> clazz = ctClass.toClass();
        // 创建对象
        AccountDao accountDao = (AccountDao) clazz.newInstance();
        // 调用方法
        accountDao.insert("aaa");
        accountDao.delete();
        accountDao.update("aaa", 1000.0);
        accountDao.selectByActno("aaa");
    }

    @Test
    public void testGenerateImpl() throws Exception {
        // 获取类池
        ClassPool pool = ClassPool.getDefault();
        // 制造类
        CtClass ctClass = pool.makeClass("cn.piggy.bank.dao.impl.AccountDaoImpl");
        // 制造接口
        CtClass ctInterface = pool.makeInterface("cn.piggy.bank.dao.AccountDao");
        // 添加接口到类中
        ctClass.addInterface(ctInterface);  // AccountDaoImpl implements AccountDao
        // 实现接口中的方法
        // 制造方法
        CtMethod ctMethod = CtMethod.make("public void delete() {System.out.println(\"hello delete!\");}", ctClass);
        // 将方法添加到类中
        ctClass.addMethod(ctMethod);

        // 在内存中生成类，同时将生成的类加载到JVM中
        Class<?> clazz = ctClass.toClass();
        AccountDao accountDao = (AccountDao) clazz.newInstance();
        accountDao.delete();
    }

    @Test
    public void testGenerateFirstClass() throws Exception {
        // 获取类池，这个类池就是用来生成class的
        ClassPool pool = ClassPool.getDefault();
        // 制造类（需要告知类名）
        CtClass ctClass = pool.makeClass("cn.piggy.bank.dao.impl.AccountDaoImpl");
        // 制造方法
        String methodCode = "public void insert() {System.out.println(123);}";
        CtMethod ctMethod = CtMethod.make(methodCode, ctClass);
        // 将方法添加到类中
        ctClass.addMethod(ctMethod);
        // 在内存中生成class
        ctClass.toClass();

        // 类加载到JVM中，返回AccountDaoImpl类的字节码文件
        Class<?> clazz = Class.forName("cn.piggy.bank.dao.impl.AccountDaoImpl");
        // 创建对象
        Object obj = clazz.newInstance();
        // 获取AccountDaoImpl中的insert方法
        Method insertMethod = clazz.getDeclaredMethod("insert");
        // 调用方法insert
        insertMethod.invoke(obj);
    }

}
