package learn.maoyu.oop.exam;

public class Company {
    public static void main(String[] args) {
        Employee[] employees = {new Developer("开发1"), new Manager("毛经理", 15000.0)};
        for (Employee employee : employees) {
           employee.showInfo();
           employee.work();
           if (employee instanceof Drivable) {
               ((Drivable) employee).drive();
           }
        }
    }
}
