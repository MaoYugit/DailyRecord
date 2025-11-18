package learn.maoyu.oop.exam;

public abstract class Employee {
    private String id;
    private String name;
    private static int counter = 0; //用于自动生成ID

    public Employee(String name) {
        this.name = name;
        counter++;
        this.id = "EMP-" + counter;
    }

    public void showInfo() {
        System.out.println("name: " + this.name);
        System.out.println("id: " + this.id);
    }

    public abstract void work();

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
