package cn.piggy.client;

import cn.piggy.annotation.Component;

import java.io.File;
import java.lang.reflect.Array;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class ComponentScan {
    public static void main(String[] args) {
        // 用于存放实例化bean的集合
        Map<String, Object> beanMap = new HashMap<>();

        // 目前只知道一个包的名字，扫描这个包下所有的类，
        // 当这个类上有@Component注解的时候，实例化该对象，然后方法Map集合中。
        String packageName = "cn.piggy.bean";

        // 扫描目录下所有文件
        String packagePath = packageName.replaceAll("\\.", "/");

        // cn是类的根目录下的一个目录，获取其绝对路径
        URL url = ClassLoader.getSystemClassLoader().getResource(packagePath);
        String path = url.getPath();

        // 获取该绝对路径下的所有文件
        File file = new File(path);
        File[] files = file.listFiles();    // 获取该目录下的所有文件
        Arrays.stream(files).forEach(f -> {
            try {
                // System.out.println(f.getName().split("\\.")[0]);
                String className = packageName + "." + f.getName().split("\\.")[0];
                // 通过反射机制解析注解
                Class<?> aClass = Class.forName(className);
                // 判断类上是否有这个注解
                if (aClass.isAnnotationPresent(Component.class)) {
                    // 获取注解
                    Component annotation = aClass.getAnnotation(Component.class);
                    String id = annotation.value();
                    // 有该注解的都要实例化对象
                    Object obj = aClass.newInstance();
                    beanMap.put(id, obj);
                }
            } catch(Exception e){
                throw new RuntimeException(e);
            }
        });
        System.out.println(beanMap);
    }
}
