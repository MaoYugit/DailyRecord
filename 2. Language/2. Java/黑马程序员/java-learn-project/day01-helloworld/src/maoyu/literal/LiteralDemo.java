package maoyu.literal;
/**
 *  目标：掌握常见字面量的书写格式
 */
public class LiteralDemo {
    public static void main(String[] args) {
        printLiteral();
    }
    public static void printLiteral() {
        // 打印常见字面量
        // 打印布尔值 true
        System.out.println(true);
        // 打印布尔值 false
        System.out.println(false);
        // 打印整数 10
        System.out.println(10);
        // 打印浮点数 10.5
        System.out.println(10.5);
        // 打印字符 'a'
        System.out.println('a');
        // 打印中文字符 '中'
        System.out.println('中');
        // 打印字符串 "hello world"
        System.out.println("hello world");
        // 打印包含换行符的字符串，\n 表示换行
        System.out.println("hello \nworld");
        // 打印包含制表符的字符串，\t 表示制表符
        System.out.println("hello \tworld");
        // 打印包含退格符的字符串，\b 表示退格
        System.out.println("hello \bworld");
        // 打印包含换页符的字符串，\f 表示换页
        System.out.println("hello \fworld");
        // 打印包含回车符的字符串，\r 表示回车
        System.out.println("hello \rworld");
        // 打印包含单引号的字符串，\' 表示单引号转义
        System.out.println("hello \'world");
        // 打印包含双引号的字符串，\" 表示双引号转义
        System.out.println("hello \"world");
        // 打印包含反斜杠的字符串，\\ 表示反斜杠转义
        System.out.println("hello \\world");

    }
}
