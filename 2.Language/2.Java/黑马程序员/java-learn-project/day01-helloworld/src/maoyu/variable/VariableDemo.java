package maoyu.variable;

public class VariableDemo {
    public static void main(String[] args){
        printlnVariable();
    }

    public static void printlnVariable(){
        int a = 10;
        System.out.println(a);
        System.out.println(a + 10);
        System.out.println(a - 8);

        double b = 10.5;
        System.out.println(b);
        System.out.println(b + 10.5);
        System.out.println(b - 8.5);

        char c = 'a';
        System.out.println(c);
        System.out.println(c + 1);
        System.out.println(c - 1);
        System.out.println(c + 10);

        String d = "hello world";
        System.out.println(d);
        System.out.println(d + "hello world");
        System.out.println(d + 10);
        System.out.println(d + 10.5);
        System.out.println(d + true);
        System.out.println(d + 'a');
        System.out.println(d + c);
        System.out.println(d + a);

        boolean e = true;
        /*System.out.println(e);
        System.out.println(e + true);
        System.out.println(e + false);
        System.out.println(e + 10);
        System.out.println(e + 10.5);
        System.out.println(e + 'a');
        System.out.println(e + c);*/
    }
}
