package cn.piggy.spring6.bean;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Vip {

    @Value("李四")
    private String name;

    public Vip() {
    }

    @Override
    public String toString() {
        return "Vip{" +
                "name='" + name + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
