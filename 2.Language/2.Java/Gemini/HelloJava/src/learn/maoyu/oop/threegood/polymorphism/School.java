package learn.maoyu.oop.threegood.polymorphism;

public class School {
    public static void main(String[] args) {
        Student stu = new Student("小明", 15, 99.0);
        Teacher tea = new Teacher("王老师", 35, "数学");

        stu.eat();  // 调用从 Person 继承来的 eat() 方法
        stu.study(); // 调用自己特有的 study() 方法

        tea.eat();   // 调用从 Person 继承来的 eat() 方法
        tea.teach(); // 调用自己特有的 teach() 方法

        stu.work();
        tea.work();
    }
}

