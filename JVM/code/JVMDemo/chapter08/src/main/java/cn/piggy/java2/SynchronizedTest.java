package cn.piggy.java2;

public class SynchronizedTest {
    public void f() {
        Object lock = new Object();
        synchronized (lock) {
            System.out.println(lock);
        }
    }
}
