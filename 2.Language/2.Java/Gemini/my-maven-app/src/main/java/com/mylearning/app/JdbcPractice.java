package com.mylearning.app;

import java.sql.*; // 导入所有java.sql包下的类

public class JdbcPractice {
    // --- 数据库连接信息 ---
    // URL格式: jdbc:数据库类型://主机名:端口号/数据库名?参数
    private static final String URL = "jdbc:mysql://localhost:3306/learning?useSSL=false&serverTimezone=UTC";

    private static final String USER = "root";

    private static final String PASSWORD = "123456";

    public static void main(String[] args) {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            // 1. 加载驱动类 (在JDBC 4.0后，这一步可以省略，但写上是个好习惯)
//            Class.forName("com.mysql.cj.jdbc.Driver");

            // 2. 获取数据库连接 (Connection)
            System.out.println("正在连接数据库...");
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("数据库连接成功！");

            // 3. 创建执行SQL语句的对象 (Statement)
            stmt = conn.createStatement();
            String sql = "SELECT id, username, email FROM users";

            // 4. 执行SQL语句，并接收返回的结果集 (ResultSet)
            rs = stmt.executeQuery(sql); // executeQuery用于执行SELECT查询

            // 5. 处理结果集 (ResultSet)
            System.out.println("--- 用户列表 ---");
            while (rs.next()) {
                // .next() 将光标移动到下一行，如果有数据则返回true
                // 通过列名或列索引(从1开始)获取数据
                int id = rs.getInt("id");
                String username = rs.getString("username");
                String email = rs.getString("email");

                System.out.printf("ID: %d, 用户名: %s, 邮箱: %s\n", id, username, email);
            }


        } catch (SQLException e) {
            System.err.println("数据库操作失败！");
            e.printStackTrace();
        } finally {
            // 6. 释放资源 (非常重要！必须在finally块中进行，且顺序与获取时相反)
            System.out.println("--- 释放资源 ---");
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
