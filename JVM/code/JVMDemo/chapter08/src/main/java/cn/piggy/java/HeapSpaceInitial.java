package cn.piggy.java;

public class HeapSpaceInitial {
    public static void main(String[] args) {
        // 返回java虚拟机中堆内存量（换算成MB）
        long initialMemory = Runtime.getRuntime().totalMemory() / 1024 / 1024;
        // 返回java虚拟机中试图使用的最大堆内容量（换算成MB）
        long maxMemory = Runtime.getRuntime().maxMemory() / 1024 / 1024;

        System.out.println("-Xms: " + initialMemory + "MB");    // 575MB
        System.out.println("-Xmx: " + maxMemory + "MB");        // 575MB

        /*try {
            Thread.sleep(1000000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }*/
    }
}
