package learn.maoyu.oop.deepin.interfacelearn;

public class TestFlyable {
    public static void main(String[] args) {
        Flyable f1 = new Bird("鹦鹉");
        Flyable f2 = new Plane();

        f1.fly();
        f2.fly();

        // 让一个能飞的东西飞起来
        takeOff(new Bird("麻雀"));
        takeOff(new Plane());
    }

    // 我们可以写一个只接受"能飞的"东西的方法
    public static void takeOff(Flyable f) {
        System.out.println("准备起飞...");
        f.fly();
        System.out.println("起飞成功!");
    }
}
