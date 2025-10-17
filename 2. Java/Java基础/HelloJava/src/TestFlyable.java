public class TestFlyable {
    public static void main(String[] args) {
        Flyable f1 = new Bird("鹦鹉");
        Flyable f2 = new Plane();

        f1.fly();
        f2.fly();

        takeOff(new Bird("麻雀"));
        takeOff(new Plane());
    }

    public static void takeOff (Flyable f) {
        System.out.println("准备起飞...");
        f.fly();
        System.out.println("起飞成功!");
    }
}
