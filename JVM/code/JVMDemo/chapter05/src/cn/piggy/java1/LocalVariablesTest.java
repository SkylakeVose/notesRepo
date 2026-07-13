package cn.piggy.java1;

import java.util.Date;

public class LocalVariablesTest {
    private int count = 0;

    public static void main(String[] args) {
        LocalVariablesTest test = new LocalVariablesTest();
        int num = 10;
        test.test1();
    }

    public void test1() {
        Date date = new Date();
        String name1 = "cn.piggy";
        String info = test2(date, name1);
        System.out.println(info);
    }

    public String test2(Date dateP, String name2) {
        dateP = null;
        name2 = "piggy";
        double weight = 120.5;
        char gender = '男';
        return dateP + name2;
    }

    public void test3() {
        count++;
    }

    public void test4() {

    }
}
