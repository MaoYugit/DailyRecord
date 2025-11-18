package learn.maoyu.oop.deepin.interfacelearn;

import learn.maoyu.oop.deepin.abstractclass.Animal;

public class Bird extends Animal implements Flyable{
    public Bird(String name) {
        super(name);
    }

    @Override
    public void makeSound() {
        System.out.println(getName() + " 发出声音: 叽叽喳喳!");
    }

    // 必须实现 Flyable 接口中的 fly 方法
    @Override
    public void fly() {
        System.out.println("小鸟 " + getName() + " 正在扇动翅膀飞翔。");
    }
}
