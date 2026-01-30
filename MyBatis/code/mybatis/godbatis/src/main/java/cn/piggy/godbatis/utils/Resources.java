package cn.piggy.godbatis.utils;

import java.io.InputStream;

/**
 * godBatis框架提供的一个工具类
 * 这个工具类专门完成类路径中资源的加载
 */
public class Resources {
    /**
     * 工具了的构造方法都是建议私有化的
     * 因为工具类中的方法都是静态的，不能创建对象就能调佣。
     * 为了避免new对象，所有构造方法私有化。
     * 这是一种编程习惯。
     */
    private Resources() {}

    /**
     * 从类路径中加载资源。
     * @param resource 放在类路径当中的资源文件
     * @return 访问资源文件的一个输入流
     */
    public static InputStream getResourceAsStream(String resource) {
        return ClassLoader.getSystemClassLoader().getResourceAsStream(resource);
    }
}
