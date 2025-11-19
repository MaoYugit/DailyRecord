package com.mylearning.app;

import java.sql.*;

public class UserDao {
    // --- 数据库连接信息 ---
    // URL格式: jdbc:数据库类型://主机名:端口号/数据库名?参数
    private static final String URL = "jdbc:mysql://localhost:3306/learning?useSSL=false&serverTimezone=UTC";

    private static final String USER = "root";

    private static final String PASSWORD = "123456";

    Connection conn = null;
    Statement stmt = null;
    ResultSet rs = null;


    // --- 查询单个用户 ---
    public void findUserById(int userId) {
        // 使用 try-with-resources 自动关闭资源
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
            // 使用 ?作为参数占位符
            String sql = "SELECT * FROM users WHERE id = ?";
            // 创建 PreparedStatement 对象
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                // 设置参数：第一个问号，值为userId
                pstmt.setInt(1, userId);
                // 执行查询
                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        System.out.printf("找到用户: ID: %d, 用户名: %s\n", rs.getInt("id"), rs.getString("username"));

                    } else {
                        System.out.println("未找到ID为 " + userId + " 的用户。");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // --- 插入新用户 ---
    public void addUser(String username, String email) {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement("INSERT INTO users (username, email) VALUES (?, ?)")) {

            pstmt.setString(1, username);
            pstmt.setString(2, email);

            // .executeUpdate() 用于执行增、删、改操作，返回受影响的行数
            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("用户 " + username + " 添加成功！");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 在 main 方法中测试
    public static void main(String[] args) {
        UserDao dao = new UserDao();
//        dao.addUser("David", "david@example.com");
        dao.findUserById(7); // 查找刚刚添加的用户
    }
}
