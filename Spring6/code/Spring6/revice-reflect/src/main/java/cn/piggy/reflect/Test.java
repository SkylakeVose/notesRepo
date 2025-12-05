package cn.piggy.reflect;

public class Test {
    public static void main(String[] args) {

        // 不使用反射机制
        SomeService someService = new SomeService();
        someService.doSome();

        String s1 = someService.doSome("张三");
        System.out.println(s1);

        String s2 = someService.doSome("李四", 100);
        System.out.println(s2);

    }
}
