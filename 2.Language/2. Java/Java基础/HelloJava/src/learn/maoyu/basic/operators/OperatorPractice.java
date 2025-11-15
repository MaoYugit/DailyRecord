package learn.maoyu.basic.operators;

public class OperatorPractice {
    public static void main(String[] args) {
        int a = 10;
        int b = 3;

        // 算术运算符
        System.out.println("a + b = " + (a + b)); // 13
        System.out.println("a - b = " + (a - b)); // 7
        System.out.println("a * b = " + (a * b)); // 30
        System.out.println("a / b = " + (a / b)); // 3 (整数除法，小数部分被舍去)
        System.out.println("a % b = " + (a % b)); // 1 (10除以3的余数)

        // 比较运算符
        boolean isAGreaterThanB = a > b;
        System.out.println("a > b吗？ " + isAGreaterThanB); // true
        System.out.println("a == b吗？ " + (a == b)); // false

        // 逻辑运算符
        int score = 85;
        boolean isExcellent = score >= 80 && score <= 90;
        System.out.println("分数是否在80-90之间？ " + isExcellent); // true
    }
}