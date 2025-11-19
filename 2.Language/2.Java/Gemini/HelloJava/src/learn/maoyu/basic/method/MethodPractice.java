package learn.maoyu.basic.method;

public class MethodPractice {
    public static void main(String[] args) {
        System.out.println("程序开始！");
        // 调用我们自己写的方法
        printSeparator();

        System.out.println("自我介绍：");
        System.out.println("我正在学习Java！");

        // 再次调用，实现复用
        printSeparator();

        System.out.println("程序结束！");

        greet("MaoYu");

        // 调用带返回值的方法，并用一个变量来接收返回的结果
        int result1 = sum(10, 20);
        System.out.println("10 + 20 = " + result1);

    }

    // --- 在 main 方法的外面，但在 class 的里面，定义我们的新方法 ---

    /**
     * 这个方法的功能是打印一条分割线。
     * 它没有参数，也没有返回值。
     */
    public static void printSeparator() {
        System.out.println("-----------------------------");
    }

    /**
     * 这个方法向指定的人打招呼。
     * @param name (这是一个 String 类型的"形参"，作为输入)
     */
    public static void greet(String name) {
        System.out.println("你好, " + name + "! 欢迎来到Java的世界！");
    }

    /**
     * 计算两个整数的和。
     * @param a 第一个整数
     * @param b 第二个整数
     * @return 返回它们的和 (int 类型)
     */
    public static int sum(int a, int b) {
        return a + b; // 使用 return 关键字返回计算结果
    }
}
