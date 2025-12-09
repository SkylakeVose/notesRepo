package cn.piggy.client;

import cn.piggy.annotation.Component;

public class ReflectAnnotationTest1 {
    public static void main(String[] args) throws Exception {
        // 通过反射机制怎么读取注解
        // 获取类
        Class<?> aClass = Class.forName("cn.piggy.bean.User");
        // 判断类上面有没有这个注解
        if (aClass.isAnnotationPresent(Component.class)) {
            // 获取类上的注解
            Component annotation = aClass.getAnnotation(Component.class);
            // 访问注解属性
            System.out.println(annotation.value());
        }
    }
}
