package atguigu01.oop;

public class PhoneTest {    // 是Phone类的测试类
    public static void main(String[] args) {
        // 创建Phone的对象
        Phone p1 = new Phone();

        // 通过Phone的对象，调用其内部声明的属性和方法
        p1.name = "huawei mate70";
        p1.price = 7999;

        System.out.println("name=" + p1.name + ", price=" + p1.price);

        // 调用方法
        p1.call();
        p1.sendMessage("有内鬼，终止交易!");
        p1.playGame();
    }
}
