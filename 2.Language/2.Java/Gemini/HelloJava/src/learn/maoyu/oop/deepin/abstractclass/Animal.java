package learn.maoyu.oop.deepin.abstractclass;

public abstract class Animal {
    private String name;

    public Animal(String name) {
        this.name = name;
    }

    public abstract void makeSound();

    public void sleep(){
        System.out.println(this.name + " is Sleeping Zzz...");
    }

    public String getName() { return name; }
}
