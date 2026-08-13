package cn.piggy.java2;

public class EscapeAnalysis {

    public EscapeAnalysis obj;

    // 方法返回EscapeAnalysis对象，发生逃逸
    public EscapeAnalysis getInstance() {
        return obj == null ? new EscapeAnalysis() : obj;
    }

    // 为成员属性数值，发生逃逸
    public void setObj() {
        this.obj = new EscapeAnalysis();
    }

    // 对象的作用域仅在当前方法中被使用，没有发生逃逸
    public void useEscapeAnalysis() {
        EscapeAnalysis e = new EscapeAnalysis();
    }

    // 引用成员变量的值，发生逃逸
    public void useEscapeAnalysis1() {
        EscapeAnalysis e = getInstance();
    }
}
