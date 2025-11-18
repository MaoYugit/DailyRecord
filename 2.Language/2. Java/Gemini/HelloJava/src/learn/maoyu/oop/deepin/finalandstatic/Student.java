package learn.maoyu.oop.deepin.finalandstatic;

public class Student {
    private String name;
    public static int studentCount = 0; // 静态变量，属于Student类

    public Student(String name) {
        this.name = name;
        studentCount++; // 每创建一个学生，计数器加1
    }

    public static void main(String[] args) {
        System.out.println("创建学生前，总数: " + Student.studentCount); // 直接用类名调用
        Student s1 = new Student("A");
        Student s2 = new Student("B");
        System.out.println("创建学生后，总数: " + Student.studentCount); // 输出2
    }
}
