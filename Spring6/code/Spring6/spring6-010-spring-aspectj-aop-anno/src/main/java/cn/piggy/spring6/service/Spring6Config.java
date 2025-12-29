package cn.piggy.spring6.service;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration  // 代替spring配置文件
@ComponentScan({"cn.piggy.spring6.service"})    // 组件扫描
@EnableAspectJAutoProxy(proxyTargetClass = true)    // 启用autoProxy代理
public class Spring6Config {
}
