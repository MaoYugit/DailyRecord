package learn.maoyu.oop.exam;

public class Developer extends Employee {

    public Developer(String name) {
        super(name);
    }

    @Override
    public void work(){
        System.out.println("正在写代码，解决BUG");
    }
}
