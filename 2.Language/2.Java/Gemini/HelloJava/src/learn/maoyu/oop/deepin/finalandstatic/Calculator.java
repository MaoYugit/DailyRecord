package learn.maoyu.oop.deepin.finalandstatic;

public class Calculator {
    // 这是一个工具类，所有方法都是静态的，无需创建对象
    public static int add(int a, int b) {
        return a + b;
    }

    public static void main(String[] args) {
        // 使用
        int sum = Calculator.add(5, 3); // 直接调用
        System.out.println(sum);
    }
}