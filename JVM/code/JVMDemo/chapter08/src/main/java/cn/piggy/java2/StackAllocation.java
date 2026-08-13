package cn.piggy.java2;

public class StackAllocation {
    public static void main(String[] args) {
        long start = System.currentTimeMillis();

        for (int i = 0; i < 10000000; i++) {
            alloc();
        }

        // 查看执行时间
        long end = System.currentTimeMillis();
        System.out.println("花费时间：" + (end - start) + "ms");

        // 为了方便查看内存个数，进行延时
        try {
            Thread.sleep(1000000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void alloc() {
        User user = new User(); // 未发生逃逸
    }

    static class User {

    }
}
