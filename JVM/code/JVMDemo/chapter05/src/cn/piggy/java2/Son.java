package cn.piggy.java2;

class Father {
    public Father() {
        System.out.println("father的构造器");
    }

    public static void showStatic(String str) {
        System.out.println("father " + str);
    }

    public final void showFinal() {
        System.out.println("father show final");
    }

    public void showCommon() {
        System.out.println("father 普通方法");
    }
}

public class Son extends Father {
    public Son() {
        // invokespecial
        super();
    }

    public Son(int age) {
        // invokespecial
        this();
    }

    // 不是重写的父类的静态方法，因此静态方法不能被重写！
    public static void showStatic(String str) {
        System.out.println("Son " + str);
    }

    private void showPrivate(String str) {
        System.out.println("son private " + str);
    }

    public void show() {
        // invokestatic
        showStatic("PIGGY");
        // invokestatic
        super.showStatic("good!");
        // invokestatic
        showPrivate("hello!");
        // invokestatic
        super.showCommon();

        // invokevirtual（虽然调用了invokevirtual，但仍是非虚方法）
        showFinal();
        // 如果指定调用父类的final方法，则是调用了invokespecial
        super.showFinal();

        // 以下都为invokevirtual（虚方法）
        showCommon();
        info();

        MethodInterface in = null;
        in.methodA();   // invokeinterface（虚方法）
    }

    public void info() {
    }

    public void display(Father f) {
        f.showCommon();
    }

    public static void main(String[] args) {
        Son son = new Son();
        son.show();
    }
}

interface MethodInterface {
    void methodA();
}
