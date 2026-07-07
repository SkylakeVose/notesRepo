package cn.piggy.java1;

import java.sql.DriverManager;

public class ClassLoaderTest2 {
    public static void main(String[] args) {
        try {
            // 1. 获取当前类的ClassLoader
            ClassLoader classLoader = Class.forName("java.lang.String").getClassLoader();
            System.out.println(classLoader);    // null

            // 2. 获取当前线程上下文的ClassLoader
            ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
            System.out.println(contextClassLoader);

            // 3. 获取系统的ClassLoader
            ClassLoader systemClassLoader = ClassLoader.getSystemClassLoader();
            System.out.println(systemClassLoader);
            // 通过系统类加载器获取应用类加载器
            ClassLoader appClassLoader = systemClassLoader.getParent();
            System.out.println(appClassLoader);

            // 4. 获取调用者的ClassLoader(sql相关)
            // DriverManager.getCallerClassLoader();

        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }
}
