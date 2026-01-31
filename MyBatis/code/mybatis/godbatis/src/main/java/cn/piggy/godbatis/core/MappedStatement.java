package cn.piggy.godbatis.core;

/**
 * 普通的java类。POJO 封装了一个SQL标签
 * 一个MappedStatment对象对应一个SQL标签
 * 一个SQL标签中的所有信息封装到MappedStatement对象当中。
 * 面向对象编程思想。
 */
public class MappedStatement {
    /**
     * sql语句
     */
    private String sql;

    /**
     * 要封装的结果集类型，有的时候为null
     * 比如:insert delete update语句的时候为null，在select语句时才有值
     */
    private String resultType;

    public MappedStatement() {
    }

    public MappedStatement(String sql, String resultType) {
        this.sql = sql;
        this.resultType = resultType;
    }

    @Override
    public String toString() {
        return "MappedStatement{" +
                "sql='" + sql + '\'' +
                ", resultType='" + resultType + '\'' +
                '}';
    }

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public String getResultType() {
        return resultType;
    }

    public void setResultType(String resultType) {
        this.resultType = resultType;
    }
}
