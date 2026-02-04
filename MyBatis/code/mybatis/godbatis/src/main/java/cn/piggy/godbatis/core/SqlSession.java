package cn.piggy.godbatis.core;

import java.lang.reflect.Method;
import java.sql.*;

/**
 * 专门负责处理SQL语句的会话对象
 */
public class SqlSession {
    private SqlSessionFactory factory;

    public SqlSession(SqlSessionFactory sqlSessionFactory) {
        this.factory = sqlSessionFactory;
    }

    /**
     * 提交事务
     */
    public void commit() {
        factory.getTransaction().commit();
    }

    /**
     * 回滚事务
     */
    public void rollback() {
        factory.getTransaction().rollback();
    }

    /**
     * 关闭事务
     */
    public void close() {
        factory.getTransaction().close();
    }

    /**
     * 执行insert语句，向数据库表当中插入记录
     * @param sqlId sql语句的id
     * @param pojo 插入的数据
     * @return
     */
    public int insert(String sqlId, Object pojo) {
        int count = 0;
        try {
            // JDBC代码，执行insert语句，完成插入操作
            Connection connection = factory.getTransaction().getConnection();
            // insert into t_user values (#{id}, #{name}, #{age})
            String godbatisSql = factory.getMappedStatements().get(sqlId).getSql();
            // insert into t_user values (?, ?, ?)
            String sql = godbatisSql.replaceAll("#\\{[a-zA-Z0-9_$]*}", "?");
            PreparedStatement ps = connection.prepareStatement(sql);
            // 给？占位传值
            // 要点：1.传值数量 2.传值顺序
            // godbatis主要考虑大致实现，类型匹配转换先不做考虑。因此godbatis只使用setString，要求数据库表中字段类型都为varchar
            int fromIndex = 0;  // 查找起始位
            int index = 1;      // 属性顺序下标
            while(true) {
                int jingIndex = godbatisSql.indexOf("#", fromIndex);
                if(jingIndex < 0) {
                    break;
                }
                int youKuoHaoIndex = godbatisSql.indexOf("}", fromIndex);
                // 获取属性名
                String propertyName = godbatisSql.substring(jingIndex + 2, youKuoHaoIndex).trim();
                fromIndex = youKuoHaoIndex + 1;

                // 通过反射机制调用相关属性的方法
                String getMethodName = "get" + propertyName.toUpperCase().charAt(0) + propertyName.substring(1);
                Method method = pojo.getClass().getDeclaredMethod(getMethodName);
                Object propertyValue = method.invoke(pojo);

                ps.setString(index, propertyValue.toString());
                index++;
            }

            // 执行SQL语句
            count = ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return count;
    }

    /**
     * 执行查询语句，返回一个对象。该方法只适合返回一条记录的sql语句
     * @param sqlId
     * @param param
     * @return
     */
    public Object selectOne(String sqlId, Object param) {
        Object object = null;
        try {
            Connection connection = factory.getTransaction().getConnection();
            MappedStatement mappedStatement = factory.getMappedStatements().get(sqlId);
            // sql查询语句
            // select * from t_user where id = #{id}
            String godbatisSql = mappedStatement.getSql();
            String sql = godbatisSql.replaceAll("#\\{[a-zA-Z0-9_$]*}", "?");
            PreparedStatement ps = connection.prepareStatement(sql);

            // 给占位符传值（只考虑只有一个占位符的情况）
            ps.setString(1, param.toString());
            // 查询返回结果集
            ResultSet rs = ps.executeQuery();

            // 获取需要返回的对象类型
            String resultType = mappedStatement.getResultType();
            // 从结果集中取数据，封装java对象
            if (rs.next()) {
                // 获取resultType的Class
                Class<?> resultTypeClass = Class.forName(resultType);
                // 调用无参数构造方法创建对象
                object = resultTypeClass.newInstance(); // Object obj = new User();
                // 给User类的id、name、age属性赋值
                ResultSetMetaData rsmd = rs.getMetaData();  // 获取结果集元数据
                int columnCount = rsmd.getColumnCount();    // 获取元数据的列数量
                for (int i = 0; i < columnCount; i++) {     // 循环列数
                    String propertyName = rsmd.getColumnName(i + 1);    // 获取当前列名字
                    // 拼接方法名
                    String setMethodName = "set" + propertyName.toUpperCase().charAt(0) + propertyName.substring(1);
                    // 获取set方法
                    Method method = resultTypeClass.getDeclaredMethod(setMethodName, String.class);
                    // 调用set方法给对象object属性赋值
                    method.invoke(object, rs.getString(propertyName));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return object;
    }
}
