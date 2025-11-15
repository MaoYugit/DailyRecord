package learn.maoyu.basic.method;

public class ProfilePrinter {
    public static void main(String[] args) {
        printMyProfile("MaoYu", 25, "男", 15000);
    }

    public static void printMyProfile(String name, int age, String gender, double salary) {
        System.out.println("--- 我的个人信息 ---");
        System.out.println("姓名:" + name);
        System.out.println("年龄：" + age);
        System.out.println("性别：" + gender);
        System.out.println("薪资：" + salary);
        System.out.println("--------------------");
    }

}
