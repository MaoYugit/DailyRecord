package maoyu.method;

public class MethodDemo3 {
    public static void main(String[] args) {
        div(10, 0);
    }
    // 在无返回值的方法中使用return；提前结束方法
    // 一个除法
    public static void div(int a, int b){
        if(b == 0){
            System.out.println("除数不能为0");
            return;
        }
        System.out.println(a / b);
    }
}
