package learn.maoyu.oop.threegood.polymorphism;

public class Shape {
    // 属性
    private String name;

    //    构造方法
    public Shape(String name) {
        this.name = name;
    }

    // getter
    public String getName() {
        return name;
    }

    // 方法
    public double calculateArea() {
        System.out.println("形状面积无法确定");
        return 0.0;
    }

    public double calculatePerimeter() {
        System.out.println("形状周长无法确定");
        return 0.0;
    }
}
