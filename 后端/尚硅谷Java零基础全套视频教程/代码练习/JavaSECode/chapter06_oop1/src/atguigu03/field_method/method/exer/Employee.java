package atguigu03.field_method.method.exer;

public class Employee {

    // 属性
    int id;         // 编号
    String name;    // 名字
    int age;        // 年龄
    double salary;  // 工资

    // 定义一个方法，用于显示员工的属性信息
    public void show() {
        System.out.println("id = " + id + ", name = " +name
                + ", age = " + age + ", salary = " + salary);
    }

    public String show1() {
        return  "id = " + id + ", name = " +name
                + ", age = " + age + ", salary = " + salary;
    }

}
