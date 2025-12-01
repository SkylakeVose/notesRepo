package cn.piggy.spring6.bean;

import java.util.Date;

public class Student {

    // java.util.Date 在Spring当中被当做简单类型，但是注入的日期格式有要求。
    // java.util.Date 在Spring当中也可以被当成非简单类型。
    private Date birth;

    public void setBirth(Date birth) {
        this.birth = birth;
    }

    @Override
    public String toString() {
        return "Student{" +
                "birth=" + birth +
                '}';
    }
}
