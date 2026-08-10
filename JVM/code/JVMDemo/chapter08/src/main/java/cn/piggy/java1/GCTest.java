package cn.piggy.java1;

import java.util.ArrayList;
import java.util.List;

public class GCTest {
    public static void main(String[] args) {
        int i = 0;
        try {
            List<String> list = new ArrayList<>();
            String a = "hello, piggy!";
            while(true) {
                list.add(a);
                a = a + a;
                i++;
            }
        } catch (Throwable t) {
            t.printStackTrace();
            System.out.println("遍历次数: " + i);
        }
    }
}
