package cn.piggy.factory.method;

public abstract class WeaponFactory {

    // 这个方法不是静态的，是实例方法
    public abstract Weapon get();
}
