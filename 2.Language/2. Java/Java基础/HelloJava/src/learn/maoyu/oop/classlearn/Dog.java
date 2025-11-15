package learn.maoyu.oop.classlearn;

public class Dog {

    String name;
    int age;
    String breed;

    public void bark(){
        System.out.println(this.name + "：汪汪汪~~~");
    }

    public  void showInfo(){
        System.out.println("我叫" + this.name + "，今年" + this.age + "岁了，是一只" + this.breed + "犬。" );
    }

    public Dog(String name, int age, String breed) {
        this.name = name;
        this.age = age;
        this.breed = breed;
    }
}
