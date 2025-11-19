package learn.maoyu.basic.ControlFlowStatement;

public class FlowControlPractice {
    public static void main(String[] args) {
        int score = 75; // 定义一个分数变量，你可以修改它来测试
        int testScore = 88;

        System.out.println("你的分数是: " + score);

        // 开始判断
        if (score >= 60) {
            // 如果分数大于或等于60，执行这里
            System.out.println("恭喜！你及格了！");
        } else {
            // 否则，执行这里
            System.out.println("很遗憾，你需要补考。");
        }

        System.out.println("程序结束。");

        System.out.println("\n--- 成绩等级评定 ---");
        if (testScore >= 90) {
            System.out.println("评级：优秀 (A)");
        } else if (testScore >= 80) {
            System.out.println("评级：良好 (B)");
        } else if (testScore >= 70) {
            System.out.println("评级：中等 (C)");
        } else if (testScore >= 60) {
            System.out.println("评级：及格 (D)");
        } else {
            System.out.println("评级：不及格 (F)");
        }
    }
}
