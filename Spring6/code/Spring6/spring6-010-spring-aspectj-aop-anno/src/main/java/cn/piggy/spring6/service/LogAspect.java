package cn.piggy.spring6.service;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

// 切面
@Component("logAspect")
@Aspect     // 切面类是需要@Aspect注解标注的
public class LogAspect {

    // 切面 = 通知 + 切点
    // 通知就是增强，就是具体的要编写的增强代码
    // 这里通知Advice 以方法的形式出现。（因为方法中可以写代码）
    // @Before注解标注的方法就是一个前置通知
    @Before("execution(* cn.piggy.spring6.service..*(..))")
    public void myAspect() {
        System.out.println("我是一个通知，我是一段增强代码...");
    }
}
