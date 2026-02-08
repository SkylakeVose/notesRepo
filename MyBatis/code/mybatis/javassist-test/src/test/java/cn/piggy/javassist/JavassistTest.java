package cn.piggy.javassist;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import org.junit.Test;

import java.lang.reflect.Method;

public class JavassistTest {

    @Test
    public void testGenerateImpl() throws Exception {
        // 获取类池
        ClassPool pool = ClassPool.getDefault();
        // 制造类
        // 制造接口
        // 添加接口到类中
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
