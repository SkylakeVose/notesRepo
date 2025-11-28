package cn.piggy.spring6.bean;

// 简单工厂模式中的工厂类角色
public class StarFactory {

    // 工厂类中有一个静态方法
    // 简单工厂模式又叫做：静态工厂方法模式
    public static Star get() {
        // 这个Star对象最终还是工厂类负责new对象
        return new Star();
    }
}
