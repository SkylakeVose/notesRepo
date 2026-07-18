package cn.piggy.java2;

/**
 * 说明早期绑定和晚期绑定的例子
 */

class Animal {
    public void eat() {
        System.out.println("动物进食"); // 早期绑定，虚方法调用：invokevirtual
    }
}

interface Huntable {
    void hunt();
}

class Dog extends Animal implements Huntable {
    @Override
    public void eat() {
        System.out.println("狗吃骨头");
    }

    @Override
    public void hunt() {
        System.out.println("狗抓耗子，多管闲事");
    }
}

class Cat extends Animal implements Huntable {

    public Cat() {
        super(); // 调用父类空参构造方法（已经确定，早期绑定：invokespecial）
    }

    public Cat(String name) {
        this(); // 调用本类空参构造方法（已经确定，早期绑定：invokespecial）
    }

    @Override
    public void eat() {
        System.out.println("猫吃鱼");
    }

    @Override
    public void hunt() {
        System.out.println("猫抓老鼠，天经地义");
    }
}

public class AnimalTest {
    public void showAnimal(Animal animal) {
        animal.eat();   // 表现为：晚期绑定，invokevirtual
    }

    public void showHunt(Huntable h) {
        h.hunt();       // 表现为：晚期绑定，invokeinterface
    }
}
