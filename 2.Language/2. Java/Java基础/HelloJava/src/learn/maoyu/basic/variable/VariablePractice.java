package learn.maoyu.basic.variable;

public class VariablePractice {
    public static void main(String[] args) {
        // 1. 声明一个整型变量来存储年龄
        int age;
        // 2. 给这个变量赋值
        age = 25;

        // 3. 声明并同时赋值一个变量来存储身高（米）
        double height = 1.80;

        // 4. 使用 System.out.println() 打印变量的值
        // 注意，打印变量时，变量名不加双引号 ""
        System.out.println("我的年龄是：");
        System.out.println(age);

        System.out.println("我的身高是：");
        System.out.println(height);

        // 5. 更酷的打印方式：用 + 号连接文字和变量
        System.out.println("我的年龄是：" + age + "岁，身高是：" + height + "米。");
    }
}
