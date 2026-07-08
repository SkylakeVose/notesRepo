package cn.piggy.java1;

public class StringTest {
    public static void main(String[] args) {
//        String str = new java.lang.String();
//        System.out.println("hello piggy!");

        StringTest stringTest = new StringTest();
        ClassLoader classLoader = stringTest.getClass().getClassLoader();
        System.out.println(classLoader);
    }
}
