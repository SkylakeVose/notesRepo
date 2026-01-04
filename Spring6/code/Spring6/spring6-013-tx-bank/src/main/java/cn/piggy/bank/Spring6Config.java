package cn.piggy.bank;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration  // 代替spring.xml配置文件，在这个类中完成配置
@ComponentScan("cn.piggy.bank")
@EnableTransactionManagement
public class Spring6Config {

    // Spring框架检测到@Bean注解，会调用这个被标注的方法，且返回对象值是一个java对象，这个java对象会自动纳入IoC容器管理。
    // 返回的对象就是Spring容器中的一个Bean了，并且这个Bean的名字是:dataSource
    @Bean(name = "dataSource")
    public DruidDataSource getDataSource() {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://8.134.254.79:3306/spring6");
        dataSource.setUsername("test");
        dataSource.setPassword("Aa123456#.");
        return dataSource;
    }

    @Bean(name = "jdbcTemplate")
    // Spring在调用这个方法的时候，会自动传递过来一个dataSource对象
    public JdbcTemplate getJdbcTemplate(DataSource dataSource) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        jdbcTemplate.setDataSource(dataSource);
        return jdbcTemplate;
    }

    @Bean(name = "txManager")
    public DataSourceTransactionManager getDataSourceTransactionManager(DataSource dataSource) {
        DataSourceTransactionManager dataSourceTransactionManager = new DataSourceTransactionManager();
        dataSourceTransactionManager.setDataSource(dataSource);
        return dataSourceTransactionManager;
    }
}
