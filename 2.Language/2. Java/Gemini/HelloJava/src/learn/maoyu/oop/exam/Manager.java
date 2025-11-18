package learn.maoyu.oop.exam;

public class Manager extends Employee implements Drivable {
    private double bonus;

    public Manager(String name, double bonus) {
        super(name);
        this.bonus = bonus;
    }

    @Override
    public void work() {
        System.out.println("正在开会，分配任务");
    }

    @Override
    public void showInfo() {
        // 步骤1: 调用父类的 showInfo() 方法，打印 ID 和 name
        super.showInfo();
        // 步骤2: 在此基础上，增加打印奖金信息的代码
        System.out.println("Bonus: " + this.bonus);
    }

    @Override
    public void drive() {
        System.out.println("经理" + getName() + "正在开着车去谈业务");
    }
}
