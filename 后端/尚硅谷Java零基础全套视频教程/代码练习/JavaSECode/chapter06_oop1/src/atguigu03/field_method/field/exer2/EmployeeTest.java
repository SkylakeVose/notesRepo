package atguigu03.field_method.field.exer2;

public class EmployeeTest {
    public static void main(String[] args) {

        // 创建Employee的一个实例
        Employee emp1 = new Employee();

        emp1.id = 1002;
        emp1.name = "Jack"; // emp1.name = new String("Jack");
        emp1.age = 24;
        emp1.salary = 8900;

        /**
         * 赋值的另一种形式
         * MyDate myDate1 = new MyDate();
         * emp1.birthday = myDate1;
         */
        emp1.birthday = new MyDate();
        emp1.birthday.year = 2001;
        emp1.birthday.month = 1;
        emp1.birthday.day = 1;

        System.out.println("id = " + emp1.id + ", name = " + emp1.name
                + ", age = " + emp1.age + ", salary = " + emp1.salary
        + ", birthday = " + emp1.birthday.year +  "-" + emp1.birthday.month +  "-" + emp1.birthday.day);

    }
}
