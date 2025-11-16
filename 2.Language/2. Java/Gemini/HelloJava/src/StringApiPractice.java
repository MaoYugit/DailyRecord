public class StringApiPractice {
    public static void main(String[] args) {
        String text = "  Hello, Java World!  ";
        String anotherText = "hello, java world!";

        System.out.println(text.length());  // 22
        System.out.println(text.charAt(3));  // e

        System.out.println(anotherText.indexOf("java"));
        System.out.println(anotherText.lastIndexOf("l"));

        System.out.println(text.trim().equals(anotherText.trim()));
        System.out.println(text.trim().equalsIgnoreCase(anotherText));

        String sub1 = text.substring(9);
        System.out.println(sub1);
        String sub2 = text.substring(9, 13);
        System.out.println(sub2);

        System.out.println(text.toUpperCase());
        System.out.println(text.toLowerCase());

        System.out.println(text.trim());

        String data = "zhangsan,21,men";
        String[] parts = data.split(",");
        for (String part : parts) {
            System.out.println(part);
        }

        System.out.println("\n--------------------------\n");

        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append("SELECT * FROM users ");
        sqlBuilder.append("WHERE name = '张三' ");
        sqlBuilder.append("AND age > 18 ");
        sqlBuilder.append("ORDER BY id DESC;");

        String finalSql = sqlBuilder.toString();
        System.out.println(finalSql);

        sqlBuilder.insert(2, "/* 用户查询 */ ");
        String finalSql1 = sqlBuilder.toString();
        System.out.println(finalSql1);

        sqlBuilder.delete(1, sqlBuilder.length());
        String finalSql2 = sqlBuilder.toString();
        System.out.println(finalSql2);
    }
}
