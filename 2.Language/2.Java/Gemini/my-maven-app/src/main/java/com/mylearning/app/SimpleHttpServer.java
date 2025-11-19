package com.mylearning.app;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

public class SimpleHttpServer {
    public static void main(String[] args) throws IOException {
        // 1. 创建一个HttpServer实例，监听8080端口
        int port = 8080;
        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);

        // 2. 为不同的URL路径创建“上下文”(Context)，并绑定一个处理器(Handler)
        // 访问 http://localhost:8080/hello 时，由 HelloHandler 来处理
        server.createContext("/hello", new HelloHandler());

        // 访问 http://localhost:8080/time 时，我们用Lambda表达式来创建一个更简洁的处理器
        server.createContext("/time", exchange -> {
            // 准备响应内容
            String responseText = "Current time is: " + LocalDateTime.now();

            // 设置响应头：状态码200，响应体长度
            exchange.sendResponseHeaders(200, responseText.getBytes().length);

            // 获取输出流，并写入响应内容
            OutputStream os = exchange.getResponseBody();
            os.write(responseText.getBytes());
            os.close();
        });

        // 3. 设置服务器的执行器（使用默认即可）
        server.setExecutor(null);

        // 4. 启动服务器
        server.start();

        System.out.println("Server is listening on port " + port);
    }

    // 这是一个静态内部类，专门用来处理 /hello 路径的请求
    static class HelloHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            // 1. 准备要返回给浏览器的响应内容 (一个简单的HTML)
            String responseText = "<h1>Hello from my very own Java Server!</h1>";

            // 2. 设置响应头(Headers)
            // 告诉浏览器，我返回的是HTML内容，并且用UTF-8编码
            exchange.getResponseHeaders().set("Content-Type", "text/html; charset=UTF-8");

            // 3. 发送响应头，并指定状态码为200(OK)，以及响应体的字节长度
            exchange.sendResponseHeaders(200, responseText.getBytes(StandardCharsets.UTF_8).length);

            // 4. 获取响应体的输出流(OutputStream)
            OutputStream os = exchange.getResponseBody();

            // 5. 将我们的响应内容写入输出流
            os.write(responseText.getBytes(StandardCharsets.UTF_8));

            // 6. 关闭输出流，完成响应
            os.close();
        }
    }
}