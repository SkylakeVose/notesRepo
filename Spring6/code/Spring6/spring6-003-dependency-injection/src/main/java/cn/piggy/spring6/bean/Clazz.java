package cn.piggy.spring6.bean;

// 班级
public class Clazz {

    private String name;

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "clazz{" +
                "name='" + name + '\'' +
                '}';
    }
}
