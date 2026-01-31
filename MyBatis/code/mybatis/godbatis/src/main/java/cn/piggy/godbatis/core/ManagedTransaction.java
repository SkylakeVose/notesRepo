package cn.piggy.godbatis.core;

import java.sql.Connection;

/**
 * MANAGED事务管理器（godbatis对此不实现）
 */
public class ManagedTransaction implements  Transaction {
    @Override
    public void commit() {

    }

    @Override
    public void rollback() {

    }

    @Override
    public void close() {

    }

    @Override
    public void openSession() {

    }

    @Override
    public Connection getConnection() {
        return null;
    }
}
