package cn.piggy.reflect;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class Test4 {
    public static void main(String[] args) throws Exception {

        String className = "cn.piggy.reflect.User";
        String propertyName = "age";

        // 通过反射机制来调用setAge()方法
        // 获取类
        Class<?> clazz = Class.forName(className);

        // 获取方法名
        String setMethodName = "set" + propertyName.toUpperCase().charAt(0) + propertyName.substring(1);
        // 根据属性名获取属性类型
        Field field = clazz.getDeclaredField(propertyName);
        // 获取方法
        Method setMethod = clazz.getDeclaredMethod(setMethodName, field.getType());
        // 准备对象
        Object obj = clazz.newInstance();
        // 调用方法
        setMethod.invoke(obj, 18);

        System.out.println(obj);
    }
}
