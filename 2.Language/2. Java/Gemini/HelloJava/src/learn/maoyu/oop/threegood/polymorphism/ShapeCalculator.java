package learn.maoyu.oop.threegood.polymorphism;

public class ShapeCalculator {
    public static void main(String[] args) {
        Shape[] shapes = new Shape[2];
        shapes[0] = new Circle(5.0);
        shapes[1] = new Rectangle(5.0, 6.0);

        for (Shape shape : shapes) {
            System.out.println(shape.calculateArea());
            System.out.println(shape.calculatePerimeter());


        }
    }
}
