package learn.maoyu.oop.threegood.polymorphism;

public class Circle extends Shape {
    // 属性
    private double radius;

    //    构造方法
    public Circle(double radius) {
        super("圆形");
        this.radius = radius;
    }

    // 重载
    @Override
    public double calculateArea() {
        return Math.PI * this.radius * this.radius;
    }

    @Override
    public double calculatePerimeter() {
        return 2 * Math.PI * this.radius;
    }

    public static void main(String[] args) {
        Circle circle1 = new Circle(3.0);

        System.out.println(circle1.calculateArea());
        System.out.println(circle1.calculatePerimeter());

    }
}
