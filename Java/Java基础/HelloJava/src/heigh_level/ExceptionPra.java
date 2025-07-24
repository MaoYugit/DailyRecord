package heigh_level;

public class ExceptionPra {
    public static void main(String[] args) {
        try {
            int a = 10;
            int b = 0;

            int c = a / b;

            System.out.println(c);
        } catch (ArithmeticException e) {
            System.out.println(e.getMessage());
        } finally{
            System.out.println("finally");
        }
        simulateFileOperation();
    }
    public static void simulateFileOperation() {
        System.out.println("\n--- 模拟文件操作 ---");
        // 假设 file 是一个需要被关闭的资源
        Object file = null;
        try {
            System.out.println("1. 打开文件资源...");
            file = new Object(); // 模拟文件被成功打开

            // 模拟在读写文件时可能发生错误
            if (true) { // 我们强制让它出错
                throw new RuntimeException("读取文件时磁盘满了！");
            }

            System.out.println("2. 文件读写成功..."); // 这句不会被执行
        } catch (Exception e) {
            System.out.println("捕获到异常: " + e.getMessage());
            // 就算这里处理了异常，我们还是需要关闭文件
        } finally {
            // 3. 无论成功还是失败，finally 块总是会被执行
            System.out.println("进入 finally 块...");
            if (file != null) {
                System.out.println("关闭文件资源！");
                file = null;
            }
        }
    }
}
