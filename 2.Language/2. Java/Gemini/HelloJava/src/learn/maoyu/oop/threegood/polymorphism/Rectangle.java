package learn.maoyu.oop.threegood.polymorphism;

public class Rectangle extends Shape {
    private double width;
    private double height;

    public Rectangle(double width, double height) {
        super("矩形");
        this.width = width;
        this.height = height;
    }

    @Override
    public double calculateArea() {
        return width * height;
    }

    @Override
    public double calculatePerimeter() {
        return 2 * (width + height);
    }

    public static void main(String[] args) {
        Rectangle rectangle = new Rectangle(3.0, 2.0);
        System.out.println(rectangle.calculateArea());
        System.out.println(rectangle.calculatePerimeter());

    }
}
