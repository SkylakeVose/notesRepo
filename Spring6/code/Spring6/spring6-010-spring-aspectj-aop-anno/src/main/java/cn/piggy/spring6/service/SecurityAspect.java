package cn.piggy.spring6.service;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Order(1)
public class SecurityAspect {
    // 前置通知
    @Before("cn.piggy.spring6.service.LogAspect.myPointCut()")
    public void beforeAdvice() {
        System.out.println("安全前置通知...");
    }
}
