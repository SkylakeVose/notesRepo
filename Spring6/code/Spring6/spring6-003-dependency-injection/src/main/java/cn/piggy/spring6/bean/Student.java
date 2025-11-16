package cn.piggy.spring6.bean;

// 学生类
public class Student {

    // 姓名
    private String name;
    // 所属班级
    private Clazz clazz;

    public void setClazz(Clazz clazz) {
        this.clazz = clazz;
    }

    // 使用级联属性，必须提供get方法
    public Clazz getClazz() {
        return clazz;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", clazz=" + clazz +
                '}';
    }
}
