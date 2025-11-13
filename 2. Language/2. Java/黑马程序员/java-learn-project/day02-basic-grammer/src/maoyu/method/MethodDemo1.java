package maoyu.method;

public class MethodDemo1 {
    public static void main(String[] args) {
        int sum = getSum(10, 20);
        System.out.println("和是：" +  sum);
        printHello();
    }
    public static int getSum(int a, int b){
        return a + b;
    }
    public static void printHello(){
        System.out.println("hello world");
    }
}
