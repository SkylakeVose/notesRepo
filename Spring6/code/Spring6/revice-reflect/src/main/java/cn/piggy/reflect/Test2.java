package cn.piggy.reflect;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Test2 {

    public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        // 使用反射机制
        // 获取类
        Class<?> clazz = Class.forName("cn.piggy.reflect.SomeService");

        // 获取方法
        Method doSomeMethod = clazz.getDeclaredMethod("doSome", String.class, int.class);

        // 调用方法
        /**
         * 四要素：调用哪个对象的哪个方法，穿什么参数，返回什么值
         * obj：调用对象         doSomeMethod：调用方法
         * "李四", 120：传参     retValue：返回值
         */
        Object obj = clazz.newInstance();
        Object retValue = doSomeMethod.invoke(obj, "李四", 120);
        System.out.println(retValue);
    }
}
