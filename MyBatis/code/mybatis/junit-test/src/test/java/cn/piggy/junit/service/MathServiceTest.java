package cn.piggy.junit.service;

import org.junit.Assert;
import org.junit.Test;

/**
 * 单元测试类
 * 名字规范: 需要测试的类名 + Test
 */
public class MathServiceTest {

    /**
     * 一般是一个业务方法对应一个测试方法
     * 测试方法的规范：public void testXxxx() {}
     * 测试方法名的规范：以test开始。比如测试方法sum()，对应的测试名为：testSum()
     * @Test注解非常重要，被这个注解标注的方法就是一个单元测试方法。
     */

    @Test
    public void testSum() {
        /**
         * 单元测试中有两个重要概念：
         * 1. 实际值（被测试的业务方法的真正执行结果）
         * 2. 期望值（执行了这个业务方法后，期望的执行结果）
         */
        MathService mathService = new MathService();
        // 获取实际值
        int actual = mathService.sum(1, 2);
        // 期望值
        int expected = 3;
        // 加断言进行测试
        Assert.assertEquals(expected,actual);

    }

    @Test
    public void testSub() {
        MathService mathService = new MathService();
        // 获取实际值
        int actual = mathService.sub(10, 5);
        // 期望值
        int expected = 6;
        // 加断言进行测试
        Assert.assertEquals(expected,actual);
    }
}
