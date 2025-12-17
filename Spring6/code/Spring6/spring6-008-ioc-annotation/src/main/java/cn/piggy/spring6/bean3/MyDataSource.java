package cn.piggy.spring6.bean3;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class MyDataSource {

    @Value(value = "com.mysql.cj.jdbc.Driver")
    private String driver;

    @Value("jdbc:mysql://localhost:3306/spring6")
    private String url;

    @Value("root")
    private String username;

    @Value("123456")
    private String password;

    @Override
    public String toString() {
        return "MyDataSource{" +
                "driver='" + driver + '\'' +
                ", url='" + url + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
