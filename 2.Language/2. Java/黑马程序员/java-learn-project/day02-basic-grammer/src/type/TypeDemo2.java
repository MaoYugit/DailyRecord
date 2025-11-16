package type;

/*
 * 目标：深入理解表达式中的自动类型提升规则，并掌握其常见应用场景和注意事项。
 * This class focuses on the rules of automatic type promotion in Java expressions and its implications.
 */
public class TypeDemo2 {

    public static void main(String[] args) {

        System.out.println("--- 1. 表达式自动类型提升的基本规则 ---");
        System.out.println("规则：在运算过程中，小范围类型的变量会自动转换为表达式中范围最大的类型，然后再进行运算。");
        System.out.println("提升顺序: byte, short, char -> int -> long -> float -> double");

        // 示例 1-1: int 和 double 参与运算
        int a = 10;
        double b = 2.5;
        // 在 a + b 的运算中，int类型的a会自动提升为 double 类型(10.0)
        // 然后再与 double 类型的 b (2.5)进行相加，所以最终结果是 double 类型。
        double result1 = a + b;
        System.out.println("int(10) + double(2.5) 的结果类型是 double: " + result1); // 输出 12.5

        // 如果试图用 int 接收结果，编译器会报错，因为会损失精度。
        // int wrongResult = a + b; // 这行代码会编译失败！

        System.out.println("\n--- 2. 特殊规则：byte, short, char 的类型提升 ---");
        System.out.println("规则：byte, short, char 这三种类型在参与任何算术运算时，都会首先被自动提升为 int 类型。");

        // 示例 2-1: byte 和 byte 相加
        byte b1 = 10;
        byte b2 = 20;
        // 在 b1 + b2 运算时，b1 和 b2 都会被提升为 int 类型。
        // 所以，它们相加的结果自然也是 int 类型。
        int result2 = b1 + b2;
        System.out.println("byte(10) + byte(20) 的结果类型是 int: " + result2);

        // 如果试图用 byte 接收这个 int 类型的结果，编译器会报错。
        // 因为编译器无法确定一个 int 类型的值是否在 byte 的范围内（-128 to 127）。
        // byte wrongByteResult = b1 + b2; // 这行代码会编译失败！

        // 如果你明确知道结果不会溢出，并且确实需要一个 byte 类型的结果，就必须进行强制类型转换。
        byte byteResult = (byte) (b1 + b2);
        System.out.println("通过强制转换，可以将 int 结果存回 byte: " + byteResult);

        // 示例 2-2: char 和 int 相加
        char c = 'A'; // 'A' 对应的 ASCII 码是 65
        int i = 1;
        // 运算时，char 类型的 c 被提升为 int (65)，然后与 i 相加。
        int result3 = c + i;
        // 结果是 66，它对应的字符是 'B'
        System.out.println("'A' + 1 的结果是 int: " + result3); // 输出 66
        System.out.println("将结果强转回 char: " + (char)result3); // 输出 'B'


        System.out.println("\n--- 3. 注意事项：防止计算过程中的数据溢出 ---");
        System.out.println("这是一个非常常见且重要的问题！");

        int money = 1_000_000_000; // 10亿 (1 billion)
        int years = 10;

        // 目标：计算 10 年的总金额，预期结果是 100 亿 (10 billion)

        // 错误的做法：
        // money * years 这个表达式中，两个操作数都是 int 类型。
        // 所以计算结果也会被限定在 int 类型的范围内。
        // 100亿已经远远超出了 int 的最大值 (约21亿)。
        // 因此，计算结果会发生溢出，变成一个负数。
        // 即使你用一个 long 类型的变量来接收，也无济于事，因为计算本身在赋值之前就已经出错了。
        long totalWrong = money * years;
        System.out.println("错误的计算方式(int * int 导致溢出): " + totalWrong);

        // 正确的做法：
        // 在运算发生之前，将其中一个操作数提升为范围更大的类型 (long)。
        // (long) money 将 money 转换为了 long 类型。
        // 根据表达式自动提升规则，整个表达式 `(long) money * years` 的结果就会是 long 类型。
        // 这样计算就不会发生溢出。
        long totalRight = (long) money * years;
        System.out.println("正确的计算方式(将一个操作数提升为long): " + totalRight);

        // 另一种正确的做法 (不推荐，但原理相同):
        long totalRight2 = 1L * money * years;
        System.out.println("另一种正确的计算方式(乘以 1L): " + totalRight2);

        System.out.println("\n--- 总结 ---");
        System.out.println("1. 表达式的最终类型由表达式中的最高类型决定。");
        System.out.println("2. byte, short, char 运算时会先自动提升为 int。");
        System.out.println("3. 为防止大数运算时溢出，应在计算前将其中一个操作数主动提升为 long 类型。");
    }
}