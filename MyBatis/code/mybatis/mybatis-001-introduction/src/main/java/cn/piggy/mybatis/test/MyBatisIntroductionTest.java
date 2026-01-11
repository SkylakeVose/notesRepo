package cn.piggy.mybatis.test;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;


public class MyBatisIntroductionTest {
    public static void main(String[] args) throws IOException {
        // 获取SqlSessionFactoryBuilder对象
        SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();

        // 获取SqlSessionFactory对象
        InputStream is = Resources.getResourceAsStream("mybatis-config.xml");
        // 从类的根目录下获取配置文件的文件流
        // InputStream is = Resources.getResourceAsStream("com/mybatis-config.xml");
        // 从绝对路径获取配置文件的文件流
//        InputStream is = new FileInputStream("D:/mybatis-config.xml");
        // 一般情况下都是一个数据库对应一个SqlSessionFactory对象
        SqlSessionFactory sqlSessionFactory = sqlSessionFactoryBuilder.build(is);

        // 获取SqlSession对象
        SqlSession sqlSession = sqlSessionFactory.openSession();

        // 执行sql语句
        int count = sqlSession.insert("insertCar"); // 返回值是影响数据库表的记录条数
        System.out.println("插入了几条记录：" + count);

        // 提交事务
        // sqlSession.commit();

        // 关闭资源（只关闭资源是不会提交事务的）
        // sqlSession.close();
    }
}
