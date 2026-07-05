package cn.piggy.java;

public class ClinitTest1 {
    static class Father {
        public static int A = 1;
        static {
            A = 2;
        }
    }

    static class Son extends Father {
        public static int B = A;
    }

    public static void main(String[] args) {
        // 调用子类，需要先加载父类
        System.out.println(Son.B);  // 2
    }
}
