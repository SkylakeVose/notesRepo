package atguigu03.field_method.method.exer;

public class EmployeeTest {
    public static void main(String[] args) {
        // 创建类的实例
        Employee emp1 = new Employee();

        System.out.println(emp1);   // 类型@地址值

        emp1.id = 1001;
        emp1.name = "Tom";
        emp1.age = 24;
        emp1.salary = 7800;

        emp1.show();
        System.out.println(emp1.show());    // 编译报错，没有返回值
        System.out.println(emp1.show1());   // 正常编译

        // 创建Employee的第2个对象
        Employee emp2 = new Employee();
        emp2.show();
    }

}
