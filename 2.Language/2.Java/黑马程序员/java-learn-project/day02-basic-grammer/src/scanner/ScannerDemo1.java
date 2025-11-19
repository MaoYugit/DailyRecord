package scanner;

import java.util.Scanner;

public class ScannerDemo1 {
    public static void main(String[] args) {
        printUserInfo();
    }

    public static void printUserInfo (){
        Scanner sc = new Scanner(System.in);
        System.out.println("请输入用户名：");
        String username = sc.next();
        System.out.println("请输入密码：");
        String password = sc.next();
        System.out.println("请输入年龄：");
        int age = sc.nextInt();
        System.out.println("请输入性别：");
        char gender = sc.next().charAt(0);  // 获取字符串的第一个字符
        System.out.println("用户名：" + username + "，密码：" + password);
        System.out.println("年龄：" + age + "，性别：" + gender);
    }
}
