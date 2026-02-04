package cn.piggy.godbatis.core;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * JDBC事务管理器（godbatis支队这个管理器实现）
 */
public class JdbcTransaction implements Transaction{

    /**
     * 数据源属性
     * 面向接口编程
     */
    private DataSource dataSource;

    /**
     * 自动提交标志
     * true:自动提交    false:手动提交
     */
    private boolean autoCommit;

    /**
     * 数据库连接
     * 保证该事务管理器的连接不会变动
     */
    private Connection connection;

    @Override
    public Connection getConnection() {
        return connection;
    }

    /**
     * 创建事务管理器对象
     * @param dataSource
     * @param autoCommit
     */
    public JdbcTransaction(DataSource dataSource, boolean autoCommit) {
        this.dataSource = dataSource;
        this.autoCommit = autoCommit;
    }

    @Override
    public void commit() {
        try {
           connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void rollback() {
        try {
            connection.rollback();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void close() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void openConnection() {
        if (connection == null) {
            try {
                connection = dataSource.getConnection();
                // 开启事务
                connection.setAutoCommit(autoCommit);
            } catch (SQLException e) {
               e.printStackTrace();
            }
        }
    }
}
