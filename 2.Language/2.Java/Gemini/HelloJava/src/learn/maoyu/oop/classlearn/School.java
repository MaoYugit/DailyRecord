package learn.maoyu.oop.classlearn;

public class School {
    public static void main(String[] args){
        // --- 创建第一个学生对象 stu1 ---
        // 使用 Student 蓝图，new 一个具体的学生实例
        Student stu1 = new Student();

        // --- 使用 "对象名.属性名" 的方式给对象的属性赋值 ---
        stu1.name = "MaoYu";
        stu1.age = 18;
        stu1.score = 99.5;

        // --- 使用 "对象名.方法名()" 的方式调用对象的行为 ---
        System.out.println("学生1的信息：");
        stu1.sayHello();
        stu1.study();

        // --- 创建第二个学生对象 stu2 ---
        Student stu2 = new Student();
        stu2.name = "莉莉";
        stu2.age = 17;
        stu2.score = 99.0;

        System.out.println("学生2的信息：");
        stu2.sayHello(); // 输出：大家好，我叫 莉莉，今年 17 岁了。
    }
}

