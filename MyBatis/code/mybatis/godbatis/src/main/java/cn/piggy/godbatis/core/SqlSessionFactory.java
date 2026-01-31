package cn.piggy.godbatis.core;

import java.util.Map;

/**
 * SqlSessionFactory对象：
 *      一个数据库对应一个SqlSessionFactory对象。
 *      通过SqlSessionFactory对象可以获取SqlSession对象。（开启会话）
 *      一个SqlSessionFactory对象可以开启多个SqlSession会话。
 */
public class SqlSessionFactory {
    // 思考：SqlSessionFactory中应该定义那些属性？
    /**
     * 数据管理器属性
     * 事务管理器是可以灵活切换的
     * SqlSessionFactory类中的事务管理器应该是面向接口的
     * SqlSessionFactory类中应该有一个事务管理器接口
     */
    private Transaction transaction;

    /**
     * 存放sql语句的Map集合
     * key是sqlId
     * value是mappedStatement
     */
    private Map<String, MappedStatement> mappedStatements;

    public SqlSessionFactory() {
    }

    public SqlSessionFactory(Transaction transaction, Map<String, MappedStatement> mappedStatements) {
        this.transaction = transaction;
        this.mappedStatements = mappedStatements;
    }

    public Transaction getTransaction() {
        return transaction;
    }

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }

    public Map<String, MappedStatement> getMappedStatements() {
        return mappedStatements;
    }

    public void setMappedStatements(Map<String, MappedStatement> mappedStatements) {
        this.mappedStatements = mappedStatements;
    }
}
