package atguigu02.memory;

public class Person {
    // 属性
    String name;    // 姓名
    int age;        // 年龄
    char gender;    // 性别

    // 方法
    public void eat() {
        String food = "苹果";
        System.out.println("我吃" + food);
    }

    public void sleep(int hour) {
        System.out.println("人至少保证明天" + hour + "小时的睡眠");
    }

    public void interests(String hobby) {
        System.out.println("我的爱好是:" + hobby);
    }
}
