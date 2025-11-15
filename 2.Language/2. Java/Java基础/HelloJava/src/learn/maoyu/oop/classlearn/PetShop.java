package learn.maoyu.oop.classlearn;

public class PetShop {
    public static void main(String[] args) {
        Dog Dog1 = new Dog("Pipi", 3, "中华田园");
        Dog Dog2 = new Dog("HuiHui", 2, "Dubing");

        Dog1.bark();
        Dog1.showInfo();
        Dog2.bark();
        Dog2.showInfo();
    }
}
