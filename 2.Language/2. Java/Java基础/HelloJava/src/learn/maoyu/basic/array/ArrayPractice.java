package learn.maoyu.basic.array;

public class ArrayPractice {
    public static void main(String[] args) {
        // 用静态初始化创建一个成绩数组
        int[] studentScores = {85, 92, 78, 100, 61, 88};

        // --- 方式一：使用标准的 for 循环遍历 (最灵活) ---
        System.out.println("--- 使用标准 for 循环遍历 ---");
        // studentScores.length 是 6
        // 循环条件 i < 6, 所以 i 的取值是 0, 1, 2, 3, 4, 5
        for (int i = 0; i < studentScores.length; i++) {
            // i 就是当前元素的索引
            // studentScores[i] 就是当前索引对应的元素值
            System.out.println("第 " + (i + 1) + " 个学生的分数是: " + studentScores[i]);
        }

        // --- 方式二：使用增强 for 循环 (For-Each Loop) (更简洁) ---
        // 如果你只是想依次读取每个元素，而不关心索引，用这种方式更方便
        System.out.println("\n--- 使用增强 for 循环遍历 ---");
        // 语法： for (元素类型 临时变量名 : 数组名)
        // 它的意思是：对于 studentScores 数组中的每一个元素...
        for (int score : studentScores) {
            // ...把它取出来，放到临时的 score 变量里
            System.out.println("一个学生的分数是: " + score);
        }
    }
}