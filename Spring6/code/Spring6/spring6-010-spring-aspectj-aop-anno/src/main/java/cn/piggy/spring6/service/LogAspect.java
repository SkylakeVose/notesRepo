package cn.piggy.spring6.service;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

// 切面
@Component("logAspect")
@Aspect     // 切面类是需要@Aspect注解标注的
@Order(2)
public class LogAspect {

     /*切面 = 通知 + 切点
     通知就是增强，就是具体的要编写的增强代码
     这里通知Advice 以方法的形式出现。（因为方法中可以写代码）
     @Before注解标注的方法就是一个前置通知*/
    /*@Before("execution(* cn.piggy.spring6.service..*(..))")
    public void myAspect() {
        System.out.println("我是一个通知，我是一段增强代码...");
    }*/

    // 定义通用的切点表示式
    @Pointcut("execution(* cn.piggy.spring6.service..*(..))")
    public void myPointCut() {
        // 这个方法只是一个标记，方法名随意，方法体中也不需要写任何代码。
    }

    // 前置通知
    @Before("myPointCut()")
    public void beforeAdvice(JoinPoint joinPoint) {
        System.out.println("前置通知...");

        // 这个joinPoint在Spring容器调用这个方法的时候自动传过来
        // 可以使用这个参数来获取目标方法的签名
        Signature signature = joinPoint.getSignature();
        System.out.println(signature);

        // 通过方法的签名可以获得到一个方法的具体信息
        // 获取目标方法的方法名
        System.out.println("目标方法的方法名: " + signature.getName());
    }

    // 后置通知
    @AfterReturning("myPointCut()")
    public void afterReturningAdvice() {
        System.out.println("后置通知...");
    }

    // 环绕通知（环绕是最大的通知，在前置通知之前，在后置通知之后）
    @Around("myPointCut()")
    public void aroundAdvice(ProceedingJoinPoint joinPoint) throws Throwable {
        // 前面的代码
        System.out.println("前环绕...");

        // 执行目标
        joinPoint.proceed();    // 执行目标

        // 后面的代码
        System.out.println("后环绕...");
    }

    // 异常通知
    @AfterThrowing("myPointCut()")
    public void afterThrowingAdvice() {
        System.out.println("异常通知...");
    }

    // 最终通知（finally代码块里的通知）
    @After("myPointCut()")
    public void afterAdvice() {
        System.out.println("最终通知...");
    }
}
