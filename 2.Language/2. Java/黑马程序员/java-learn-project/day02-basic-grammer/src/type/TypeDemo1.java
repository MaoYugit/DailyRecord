package type;

/*
 * 目标：深入理解Java中的自动类型转换和强制类型转换。
 */
public class TypeDemo1 {

    public static void main(String[] args) {

        System.out.println("--- 1. 自动类型转换 (隐式转换) ---");
        System.out.println("规则：从小范围的数据类型转换到大范围的数据类型，由编译器自动完成。");
        System.out.println("可以理解为：小瓶子的水倒入大瓶子，是安全的。");
        System.out.println("转换顺序: byte -> short -> int -> long -> float -> double");
        System.out.println("          char -> int");

        // 示例 1-1: byte 转换为 int
        byte a = 20;
        int b = a; // 将byte类型的 a 赋值给 int类型的 b，自动发生转换
        System.out.println("byte(20) 自动转换为 int: " + b);

        // 示例 1-2: int 转换为 double
        int c = 100;
        double d = c; // int 自动转换为 double
        System.out.println("int(100) 自动转换为 double: " + d); // 输出 100.0

        // 示例 1-3: char 转换为 int
        char ch = 'A'; // 'A' 的 ASCII码 (或Unicode值) 是 65
        int i = ch;    // char 自动转换为 int
        System.out.println("char('A') 自动转换为 int: " + i); // 输出 65

        // 示例 1-4: 在表达式中的自动类型提升
        // 当多种类型的变量一起运算时，结果会自动提升为范围最大的那个类型
        int x = 10;
        double y = 5.5;
        // x 会被自动提升为 double 类型，然后与 y 进行运算，所以结果是 double 类型
        double result = x + y;
        System.out.println("表达式中 int + double 的结果类型自动提升为 double: " + result); // 输出 15.5

        // 特例：byte, short, char 参与运算时，会先自动提升为 int 类型
        byte b1 = 10;
        byte b2 = 80;
        // b1 和 b2 在运算时会先被提升为 int 类型，所以它们的和也是 int 类型。
        // 如果用 byte 来接收，编译器会报错，因为它不确定结果是否会超出 byte 的范围。
        // byte byteResult = b1 + b2; // 这行代码会报错!
        int intResult = b1 + b2;
        System.out.println("byte + byte 运算时，类型会自动提升为 int: " + intResult);

        System.out.println("\n--- 2. 强制类型转换 (显式转换) ---");
        System.out.println("规则：从大范围的数据类型转换到小范围的数据类型，需要手动完成。");
        System.out.println("可以理解为：大瓶子的水倒入小瓶子，可能会溢出(数据丢失或精度损失)，需要谨慎操作。");
        System.out.println("语法：(目标类型)变量名;");

        // 示例 2-1: double 转换为 int (精度丢失)
        double score = 99.8;
        // 直接将 double 赋值给 int 会报错，必须强制转换
        int roundedScore = (int) score; // 强制将 double 转换为 int
        System.out.println("double(99.8) 强制转换为 int: " + roundedScore); // 输出 99，小数部分被直接截断，不是四舍五入

        // 示例 2-2: int 转换为 byte (数据溢出)
        int largeNum = 130;
        // byte 的范围是 -128 到 127。130 超出了这个范围
        byte smallNum = (byte) largeNum;
        System.out.println("int(130) 强制转换为 byte (导致数据溢出): " + smallNum);
        // 解释：溢出后，结果会从 byte 的最小值开始重新计算。130 - 128 = 2，所以从-128开始数2个数，结果是 -126。
        // 计算方式是：(目标范围最大值 + 1) - (溢出值 - 目标范围最大值) 的相反数，或者直接看二进制补码的截断。

        // 示例 2-3: long 转换为 int (潜在的数据溢出)
        long veryLargeNum = 2147483648L; // 这个值比 int 的最大值 (2147483647) 大 1
        int intNum = (int) veryLargeNum;
        System.out.println("long(2147483648L) 强制转换为 int (导致数据溢出): " + intNum); // 输出 -2147483648 (int 的最小值)

        // 示例 2-4: char 和 int 之间的强制转换
        int code = 97;
        char character = (char) code; // 将 int 强制转换为 char
        System.out.println("int(97) 强制转换为 char: " + character); // 输出 'a'

        System.out.println("\n--- 3. 常见问题和注意事项 ---");
        // 1. boolean 类型不能参与任何类型转换
        // boolean flag = true;
        // int flagInt = (int) flag; // 这行代码会编译错误

        // 2. 浮点数转换为整数时，总是直接截断小数部分
        double pi = 3.14159;
        int approxPi = (int) pi;
        System.out.println("浮点数转换整数是截断，不是四舍五入: (int)3.14159 -> " + approxPi);

        // 3. 强制转换虽然能解决编译错误，但必须清楚可能带来的风险
        int money = 1_000_000_000; // 10亿
        int years = 10;
        // 计算总收入，期望是 100 亿
        // 错误示例：下面的计算结果会溢出，因为两个 int 相乘的结果仍然是 int
        int totalWrong = money * years;
        System.out.println("错误的计算(int溢出): " + totalWrong);
        // 正确做法：在计算前，将其中一个操作数转换为范围更大的类型(long)
        long totalRight = (long) money * years; // money 被提升为 long，整个表达式的结果就是 long 类型
        System.out.println("正确的计算(提升为long): " + totalRight);
    }
}