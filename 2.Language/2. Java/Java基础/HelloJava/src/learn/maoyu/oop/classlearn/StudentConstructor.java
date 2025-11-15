package learn.maoyu.oop.classlearn;

public class StudentConstructor {
    // 属性
    String name;
    int age;
    double score;

    // --- 1. 无参数的构造方法 (No-Arg Constructor) ---
    // 如果你不写任何构造方法，Java会默认送你一个看不见的、空的无参构造方法。
    // 但一旦你写了任何一个构造方法，Java就不再送了。
    public StudentConstructor() {
        System.out.println("一个无名学生被创建了...(无参构造方法被调用)");
    }

    // --- 2. 带参数的构造方法 (Parameterized Constructor) ---
    // 这是我们最常用的！
    public StudentConstructor(String name, int age, double score) {
        System.out.println("一个有名有姓的学生被创建了...(带参构造方法被调用)");
        this.name = name;
        this.age = age;
        this.score = score;
    }

    public void study() {
        // this 关键字代表“当前这个对象”
        System.out.println(this.name + " 正在努力学习Java！");
    }

    public void sayHello() {
        System.out.println("大家好，我叫 " + name + "，今年 " + age + " 岁了。");
    }

}
