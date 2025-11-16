package Demo;

import java.util.Scanner;

public class test1 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("请输入第一个数字：");
        double num1 = sc.nextDouble();
        System.out.println("请输入第二个数字：");
        double num2 = sc.nextDouble();
        System.out.println("请选择运算符号：+ - * /");
        char op = sc.next().charAt(0);

        calculator(num1, num2, op);
        }
    // 一个计算器方法
    public static void calculator(double a, double b, char op){
        switch(op){
            case '+':
                System.out.println(a + b);
                break;
            case '-':
                System.out.println(a - b);
                break;
            case '*':
                System.out.println(a * b);
                break;
            case '/':
                if(b == 0){
                    System.out.println("除数不能为0");
                }else{
                    System.out.println(a / b);
                }
                break;
            default:
                System.out.println("无效的运算符号");
        }
    }
}





