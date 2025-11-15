package learn.maoyu.oop.classlearn;

public class SchoolConstructor {
    public static void main(String[] args) {
        // 调用无参构造方法
        StudentConstructor stu1 = new StudentConstructor();
        stu1.name = "MaoYu";

        System.out.println("\n--------------------------\n");

        // 直接调用带参构造方法，一步到位完成创建和赋值！
        StudentConstructor stu2 = new StudentConstructor("MaoYu", 19, 98.5);
        stu2.sayHello();
        stu2.study();

        stu2.name = "MaoMao";
        stu2.sayHello();
        stu2.study();
    }
}
