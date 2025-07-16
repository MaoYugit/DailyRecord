public class ExceptionPractice {
    public static void main(String[] args) {
        System.out.println("--- 程序开始 ---");

        try {
            // 1. 把可能出错的代码放进 try 块
            System.out.println("正在尝试进行计算...");
            int a = 10;
            int b = 0;
            int result = a / b; // 异常在这里发生
            // 异常发生后，try块中从这一行往下的代码都不会被执行了
            System.out.println("计算结果是: " + result);
        } catch (ArithmeticException e) {
            // 2. 如果 try 块里发生了 "ArithmeticException" 类型的异常，就捕获它
            //    e 是一个异常对象，包含了错误的详细信息
            System.out.println("糟糕！计算出错了！");
            System.out.println("错误类型是: " + e.getClass().getName());
            System.out.println("错误信息是: " + e.getMessage());
            // 打印完整的错误堆栈信息，方便调试
            // e.printStackTrace();
        }

        System.out.println("--- 程序优雅地结束了 ---");


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