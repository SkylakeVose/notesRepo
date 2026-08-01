package cn.piggy.java3;

public class LocalvariableTest {
//    public static void method() {
//        int count = 0;
//        count++;
//        System.out.println(count);
//    }

//    private static StringBuilder sharedSb = new StringBuilder();  // 成员变量，共享
    private static StringBuffer sharedSb = new StringBuffer();  // 成员变量，共享

    public static void method() {
        StringBuffer sb = sharedSb;  	// 局部引用指向共享对象
        try {
            sb.append("he");  			// 多线程同时调用会出问题！
            Thread.sleep(200);  // 为了观察现象，在两次append()方法间插入了200ms延时
            sb.append("llo");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println(sb);
    }

    public static void main(String[] args) {
        for (int i = 0; i < 2; i++) {
            new Thread(() -> {
                System.out.println(Thread.currentThread().getName() + "执行中...");
                method();
            }).start();
        }
    }
}
