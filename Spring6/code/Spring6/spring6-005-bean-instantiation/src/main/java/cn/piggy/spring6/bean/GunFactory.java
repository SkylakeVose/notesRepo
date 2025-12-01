package cn.piggy.spring6.bean;

// 具体工厂角色
public class GunFactory {

    // 具体工厂橘色中的方法是：实例方法
    public Gun get() {
        // 实际上new的这个对象还是我们自己new的
        return new Gun();
    }
}
