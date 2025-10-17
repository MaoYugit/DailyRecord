public class Zoo {
    public static void main(String[] args) {
        Animal myDog = new Dog("WangCai");
        Animal myCat = new Cat("MiMI");

        myDog.makeSound();
        myDog.sleep();

        myCat.makeSound();
    }
}
