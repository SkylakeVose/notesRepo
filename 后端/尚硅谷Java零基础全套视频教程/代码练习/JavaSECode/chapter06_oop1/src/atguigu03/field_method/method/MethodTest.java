package atguigu03.field_method.method;

public class MethodTest {
    public static void main(String[] args) {


    }
}

class Person{
    // 属性
    String name;
    int age;
    char gender;

    // 方法
    public void eat() {
        System.out.println("人吃饭");
    }

    public void sleep(int hour) {
        System.out.println("人至少每天睡眠" + hour + "小时");
    }

    public String interest(String hobby) {
        String info = "我的爱好是" + hobby;
        System.out.println(info);
        return info;
    }

    public int getAge() {
        return age;
    }
}
