package learn.maoyu.api.strings;

public class StringBuilderPractice {
    public static void main(String[] args) {
        // 目标：高效地拼接一个SQL查询语句
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append("SELECT * FROM users ");
        sqlBuilder.append("WHERE name = '张三' ");
        sqlBuilder.append("AND age > 18 ");
        sqlBuilder.append("ORDER BY id DESC;");

        System.out.println("刚开始的样子：" + sqlBuilder);

        // 最后，当所有拼接完成后，再把它变回一个String对象
        String finalSql = sqlBuilder.toString();
        System.out.println("\n最终拼接的SQL: " + finalSql);

        // 其他常用方法
        sqlBuilder.insert(0, "/* 用户查询 */ "); // 在开头插入
        sqlBuilder.delete(sqlBuilder.length() - 1, sqlBuilder.length()); // 删除末尾的分号
        System.out.println("修改后的SQL: " + sqlBuilder.toString());
    }
}
