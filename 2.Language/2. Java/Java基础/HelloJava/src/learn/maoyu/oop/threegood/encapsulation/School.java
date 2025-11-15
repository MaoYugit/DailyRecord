package learn.maoyu.oop.threegood.encapsulation;


public class School {
    public static void main(String[] args) {
        Student stu2 = new Student("MaoYu", 19, 98.5);
        stu2.sayHello();
        stu2.study();

        stu2.setName("MAOMAO");
        stu2.sayHello();
        stu2.study();

        stu2.setAge(-20);
        stu2.setScore(110);
    }
}

