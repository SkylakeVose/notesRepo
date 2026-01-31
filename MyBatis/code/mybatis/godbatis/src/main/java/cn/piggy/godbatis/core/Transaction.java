package cn.piggy.godbatis.core;

/**
 * 事务管理器接口
 * 所有的事务管理器都应该遵循该规范
 * JDBC事务管理器、MANAGED事务管理器都应该实现这个接口
 * Transaction事务管理器：提供管理事务方法
 */
public interface Transaction {
    /**
     * 提交事务
     */
    void commit();

    /**
     * 回滚事务
     */
    void rollback();

    /**
     * 关闭事务
     */
    void close();

    /**
     * 后续如需其他方法可继续添加...
     */
}
