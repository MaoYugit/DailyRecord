package learn.maoyu.oop.deepin.abstractclass;

public class Zoo {
    public static void main(String[] args) {
        // Animal animal = new Animal("某种动物"); // 错误！抽象类不能被实例化

        Animal myDog = new Dog("旺财");
        Animal myCat = new Cat("咪咪");

        myDog.makeSound(); // 调用 Dog 类重写后的方法
        myDog.sleep();     // 调用从 Animal 继承来的具体方法

        myCat.makeSound(); // 调用 Cat 类重写后的方法
        myCat.sleep();
    }
}
