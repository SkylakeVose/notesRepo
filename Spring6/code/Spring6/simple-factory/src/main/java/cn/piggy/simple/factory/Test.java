package cn.piggy.simple.factory;

public class Test {
    public static void main(String[] args) {

        // 对于工厂来说，坦克类的创建细节我不需要了解，只需要向工厂索要就好

        // 需要坦克
        Weapon tank = WeaponFactory.get("TANK");
        tank.attack();

        // 需要匕首
        Weapon dagger = WeaponFactory.get("DAGGER");
        dagger.attack();

        // 需要战斗机
        Weapon fighter = WeaponFactory.get("FIGHTER");
        fighter.attack();

    }
}
