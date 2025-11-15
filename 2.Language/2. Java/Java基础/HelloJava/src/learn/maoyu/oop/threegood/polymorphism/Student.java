package learn.maoyu.oop.threegood.polymorphism;

public class Student extends Person {
    // 属性
    // 不再需要定义 name 和 age，因为已经从 Person 继承了！
    private double score;

    // 子类的构造方法
    public Student(String name, int age, double score) {
        // 必须在第一行调用父类的构造方法来初始化继承来的属性！
        // super() 就是调用父类构造方法的意思。
        super(name, age);
        this.score = score;
    }

    // 方法
    // 学生特有的行为
    public void study() {
        System.out.println(getName() + " 正在努力学习Java！");
    }

    @Override
    public void work() {
        System.out.println("学生的工作就是学习！");
    }

    // get/ set
    public double getScore() {
        return score;
    }
    public void setScore(double score) {
        this.score = score;
    }

}
