package learn.maoyu.basic.ControlFlowStatement;

public class LoopPractice {
    public static void main(String[] args) {
        // --- 示例1：打印5次 Hello, Java! ---
        System.out.println("--- 打印5次问候 ---");
        // i=0: 这是计数器，从0开始
        // i<5: 只要i小于5，就继续循环 (0, 1, 2, 3, 4 共5次)
        // i++: 每循环一次，i的值就增加1
        for (int i = 0; i < 5; i++) {
            System.out.println("Hello, Java! (第 " + (i + 1) + " 遍)");
        }

        // --- 示例2：计算1加到100的和 ---
        System.out.println("\n--- 计算1到100的和 ---");
        int sum = 0; // 创建一个变量用来存放总和
        for (int j = 1; j <= 100; j++) {
            // 循环会从 j=1, j=2, ... 一直到 j=100
            sum = sum + j; // 累加：把当前的j加到总和sum里
        }
        System.out.println("1到100的和是: " + sum); // 结果应该是5050

        System.out.println("\n--- 模拟折纸超珠峰 ---");
        double paperThickness = 0.0001; // 纸的初始厚度（米）
        double mountEverestHeight = 8848; // 珠峰高度（米）
        int count = 0; // 折叠次数计数器

        while (paperThickness < mountEverestHeight) {
            // 条件：只要纸的厚度还小于珠峰高度，就继续循环
            paperThickness = paperThickness * 2; // 每折叠一次，厚度翻倍
            count++; // 次数加1
            System.out.println("第 " + count + " 次折叠后，厚度为: " + paperThickness + " 米");
        }
        System.out.println("总共需要折叠 " + count + " 次才能超过珠峰！");
    }

}
