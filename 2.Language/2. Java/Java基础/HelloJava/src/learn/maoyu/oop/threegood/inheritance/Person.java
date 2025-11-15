package learn.maoyu.oop.threegood.inheritance;

public class Person {
    // 属性
    private String name;
    private int age;

    // 父类的构造方法
    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    //  方法
    public void eat() {
        System.out.println(this.name + " 正在吃饭。");
    }

    // 提供 get/set 方法
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
