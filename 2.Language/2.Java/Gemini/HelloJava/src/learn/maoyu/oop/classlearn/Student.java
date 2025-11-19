package learn.maoyu.oop.classlearn;

public class Student {
    // 1. 属性 (Attributes / Member Variables)
    //    学生有什么？我们把它们定义为类的成员变量。
    String name;
    int age;
    double score;

    // 2. 行为 (Behaviors / Member Methods)
    //    学生能做什么？我们把它们定义为类的方法。
    //    注意：这里的方法前面不再需要加 static 了！
    public void study() {
        // this 关键字代表“当前这个对象”
        System.out.println(this.name + " 正在努力学习Java！");
    }

    public void sayHello() {
        System.out.println("大家好，我叫 " + name + "，今年 " + age + " 岁了。");
    }
}