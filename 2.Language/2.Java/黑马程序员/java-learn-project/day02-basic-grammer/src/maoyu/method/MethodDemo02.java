package maoyu.method;

public class MethodDemo02 {
    public static void main(String[] args) {
        print(10);
        print("hello world");
    }
    public static void print(int n){
        for (int i = 0; i < n; i++) {
            System.out.println("hello world");
        }
    }

    public static void print(String s){
        System.out.println(s);
    }
}
