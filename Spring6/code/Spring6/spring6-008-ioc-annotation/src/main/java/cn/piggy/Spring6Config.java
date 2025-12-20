package cn.piggy;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

// 编写一个类，代替Spring框架的配置文件
@Configuration
@ComponentScan({"cn.piggy.dao", "cn.piggy.service"})
public class Spring6Config {
}
