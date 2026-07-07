package cn.piggy.java1;

import com.sun.net.ssl.internal.ssl.Provider;
import sun.misc.Launcher;
import sun.security.ec.CurveDB;

import java.net.URL;

public class ClassLoaderTest1 {
    public static void main(String[] args) {
        /*System.out.println("=====启动类加载器=====");
        // 获取BootstrapClassLoader能够加载的api的路径
        URL[] urLs = Launcher.getBootstrapClassPath().getURLs();
        for (URL urL : urLs) {
            System.out.println(urL);
        }

        // 从上面的路径中随意选择一个类，来看看他的类加载器是什么？
        ClassLoader classLoader = Provider.class.getClassLoader();
        System.out.println(classLoader);    // null(->引导类加载器)*/


        System.out.println("=====扩展类加载器=====");
        String extDirs = System.getProperty("java.ext.dirs");
        for (String path : extDirs.split(";")) {
            System.out.println(path);
        }

        // 从上面路径中随意选择一个类，来看看他的类加载器是什么？
        ClassLoader classLoader = CurveDB.class.getClassLoader();
        System.out.println(classLoader);    // sun.misc.Launcher$ExtClassLoader@1b6d3586
    }
}
