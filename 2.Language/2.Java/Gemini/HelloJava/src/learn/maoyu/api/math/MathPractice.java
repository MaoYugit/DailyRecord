package learn.maoyu.api.math;

public class MathPractice {
    public static void main(String[] args) {
        System.out.println("绝对值: " + Math.abs(-10));         // 10
        System.out.println("向上取整: " + Math.ceil(3.14));     // 4.0
        System.out.println("向下取整: " + Math.floor(3.14));    // 3.0
        System.out.println("四舍五入: " + Math.round(3.5));     // 4
        System.out.println("两者中的较大值: " + Math.max(10, 20)); // 20
        System.out.println("2的3次方: " + Math.pow(2, 3));      // 8.0
        System.out.println("平方根: " + Math.sqrt(16));         // 4.0
        System.out.println("生成0.0到1.0之间的随机数: " + Math.random());

        // 生成1到100之间的随机整数
        int randomNumber = (int) (Math.random() * 100) + 1;
        System.out.println("1-100的随机整数: " + randomNumber);
    }
}
