public abstract class Animal {
    private String name;

    public Animal(String name) {
        this.name = name;
    }

    public abstract void makeSound();

    public void sleep() {
        System.out.println(this.name + " is sleeping");
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
}
