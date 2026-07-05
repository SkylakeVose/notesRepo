package cn.piggy.java;

public class ClassInitTest {

    private static int num = 1;

    static {
        num = 2;
        number = 20;

//        System.out.println(num);
//        System.out.println(number);
    }

    private static int number = 10;

    public static void main(String[] args) {
        System.out.println(ClassInitTest.num);
    }
}
