package atguigu03.field_method.field.exer1;

public class EmployeeTest {
    public static void main(String[] args) {
        // 创建类的实例
        Employee emp1 = new Employee();

        System.out.println(emp1);   // 类型@地址值

        emp1.id = 1001;
        emp1.name = "Tom";
        emp1.age = 24;
        emp1.salary = 7800;

        System.out.println("id = " + emp1.id + ", name = " + emp1.name
        + ", age = " + emp1.age + ", salary = " + emp1.salary);

        // 创建Employee的第2个对象
        Employee emp2 = new Employee();
        System.out.println("id = " + emp2.id + ", name = " + emp2.name
                + ", age = " + emp2.age + ", salary = " + emp2.salary);
    }

}
