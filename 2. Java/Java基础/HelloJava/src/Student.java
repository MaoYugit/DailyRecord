public class Student extends Person{
    private double score;

    public Student(String name, int age, double score) {
        super(name, age);
        this.score = score;
    }

    public void study() {
        System.out.println(getName() + "正在努力学习Java！");
    }

    public void sayHello() {
        System.out.println("大家好，我叫 " + getName()+ "，今年 " + getAge() + " 岁了。");
    }

    @Override
    public void work() {
        System.out.println("学生的工作就是学习！");
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
