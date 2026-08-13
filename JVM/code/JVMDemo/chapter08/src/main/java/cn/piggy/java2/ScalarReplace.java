package cn.piggy.java2;

public class ScalarReplace {
    public static class User {
        public int id;
        public String name;
    }

    public static void alloc() {
        User user = new User();    // 未发生逃逸
        user.id = 5;
        user.name = "piggy";
    }

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        for (int i = 0; i < 10000000; i++) {
            alloc();
        }
        long end = System.currentTimeMillis();
        System.out.println("花费时间: " + (end - start) + "ms");
    }
}
