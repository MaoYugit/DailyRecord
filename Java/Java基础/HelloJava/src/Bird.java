public class Bird extends Animal implements Flyable{
    public Bird(String name) {
        super(name);
    }

    @Override
    public void makeSound() {
        System.out.println(getName() + " 发出声音: 叽叽喳喳!");
    }

    @Override
    public void fly() {
        System.out.println("小鸟 " + getName() + " 正在扇动翅膀飞翔。");
    }
}
