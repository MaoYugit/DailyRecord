package learn.maoyu.api.strings;

public class StringApiPractice {
    public static void main(String[] args) {
        String text = "Hello, Java World!  ";
        String anotherText = "hello, java world!";

        // 1. 获取长度
        System.out.println("长度: " +text.length()); // 20 (包括空格)
        System.out.println("长度: " +anotherText.length());

        // 2. 获取指定索引的字符
        System.out.println("第4个字符: " + text.charAt(4));// 'e' (索引从0开始)

        // 3. 查找子串的索引
        System.out.println("Java首次出现的位置: " + text.indexOf("Java")); // 7
        System.out.println("最后一个'o'的位置: " + text.lastIndexOf( "o")); // 13

        // 4. 比较字符串
        System.out.println("内容是否相等 (区分大小写): " + text.trim().equals(anotherText)); // false
        System.out.println("内容是否相等 (忽略大小写): " + text.trim().equalsIgnoreCase(anotherText)); // true

        // 5. 截取子串
        // 从索引9开始，一直到字符串末尾
        String sub1 = text.substring(7);
        System.out.println("截取'Java World!  ': " + sub1);
        // 从索引9开始，到索引13结束 (不包括13)
        String sub2 = text.substring(7, 11);
        System.out.println("只截取'Java': " + sub2);

        // 6. 转换大小写
        System.out.println("转为大写: " + text.toUpperCase());
        System.out.println("转为小写: " + text.toLowerCase());

        // 7. 去除首尾空格
        System.out.println("去除首尾空格: '" + text.trim() + "'");

        // 8. 分割字符串
        String data = "张三,25,男";
        String[] parts = data.split(",");
        System.out.println("分割后的姓名: " + parts[0]); // 张三


    }
}
