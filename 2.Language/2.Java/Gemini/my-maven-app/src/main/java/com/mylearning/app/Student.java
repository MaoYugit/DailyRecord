package com.mylearning.app;

import com.google.gson.Gson; // 看！我们可以直接导入 com.google.gson 包了

public class Student {
    private String name;
    private int age;

    public Student(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getAge() {
        return age;
    }
    public void setAge(int age) {
        this.age = age;
    }

    public static void main(String[] args) {
        System.out.println("Hello Maven!");

        // 1. 创建一个Gson对象 (这个类来自我们下载的库)
        Gson gson = new Gson();

        // 2. 创建一个我们自己的Java对象
        Student student = new Student("Tom", 18);

        // 3. 使用gson的功能，将Java对象转为JSON字符串
        String jsonString = gson.toJson(student);

        // 4. 打印结果
        System.out.println("Java对象: " + student);
        System.out.println("转换后的JSON字符串: " + jsonString);
    }
}
