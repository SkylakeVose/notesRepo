package cn.piggy.java1;

public class StackFrameTest {
    public static void main(String[] args) {
        StackFrameTest stackFrameTest = new StackFrameTest();
        stackFrameTest.method1();
    }

    public void method1() {
        System.out.println("method1()方法开始执行...");
        try {
            method2();
        } catch (Exception e) {
            System.out.println(e);
        }
        System.out.println("method1()方法执行结束...");
    }

    public int method2() {
        System.out.println("method2()方法开始执行...");
        int i = 10;
        int m = (int) method3();
        int x = 10 / 0;
        System.out.println("method2()方法即将结束...");
        return i + m;
    }

    public double method3() {
        System.out.println("method3()方法开始执行...");
        double j = 20.0;
        System.out.println("method3()方法即将结束...");
        return j;
    }
}
