package learn.maoyu.oop.threegood.encapsulation;

public class Student {
    // 属性
    private  String name;
    private int age;
    private double score;

    // 构造方法 ---
    public Student(String name, int age, double score) {
        this.name = name;
        this.age = age;
        this.score = score;
    }

    //    方法
    public void study() {
        // this 关键字代表“当前这个对象”
        System.out.println(this.name + " 正在努力学习Java！");
    }

    public void sayHello() {
        System.out.println("大家好，我叫 " + name + "，今年 " + age + " 岁了。");
    }


//    getter 和 setter
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return this.age;
    }
    public void setAge(int age) {
        if (age > 0 && age < 120) { // 只允许设置合理的年龄
            this.age = age;
        } else {
            System.out.println("错误：年龄值不合法！");
        }
    }

    public double getScore() {
        return this.score;
    }
    public void setScore(double score) {
        if (score >= 0 && score <= 100) {
            this.score = score;
        } else {
            System.out.println("错误：分数必须在0-100之间！");
        }
    }
}

