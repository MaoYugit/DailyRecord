public class School {
    public static void main(String[] args) {
        Student stu1 = new Student("MaoYu", 18, 100);
        Teacher tea1 = new Teacher("Miss", 23, "Math");

        System.out.println("学生1的信息：");
        stu1.study();
        stu1.sayHello();
        System.out.println("这次考试我考了" + stu1.getScore() + "分");

        System.out.println("\n--------------------------\n");

        tea1.teach();
        tea1.eat();

        System.out.println("\n--------------------------\n");

        Person p1 = new Student("XiaoMing", 16, 99.0);
        Person p2 = new Teacher("Miss", 23, "Math");
        p1.work();
        p2.work();

    }

}
