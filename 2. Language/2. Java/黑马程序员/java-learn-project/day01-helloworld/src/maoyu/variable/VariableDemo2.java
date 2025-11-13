package maoyu.variable;

public class VariableDemo2 {
    public static void main(String[] args) {
        printVariable();
    }
    // 打印八种基本数据类型
    public static void printVariable() {
        // 1. 整型
        byte a = 10;
        short b = 20;
        int c = 30;
        long d = 40;
        // 注意：随便写一个整数字面量默认是int类型的，即使前面我们用了long关键字，但是它还是int类型，就算那个数字没有超过long的范围，也会因为超过int的范围报错
        // 所以希望这个数字是long类型，那么就加上L或者l
        long d2 = 6565666589L;
        System.out.println(a);
        System.out.println(b);
        System.out.println(c);
        System.out.println(d);
        System.out.println(d2);

        // 2. 浮点型
        float e = 10.5f;    // 注意：随便写一个浮点数字面量默认是double类型的，即使前面我们用了float关键字，但是它还是double类型，就算那个数字没有超过float的范围，也会因为超过double的范围报错
        double f = 20.5;
        System.out.println(e);
        System.out.println(f);

        // 3. 字符型
        char g = 'a';
        System.out.println(g);

        // 4. 布尔型
        boolean h = true;
        System.out.println(h);
    }
}
