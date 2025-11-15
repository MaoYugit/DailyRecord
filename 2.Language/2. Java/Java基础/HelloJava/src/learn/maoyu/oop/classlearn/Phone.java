package learn.maoyu.oop.classlearn;

public class Phone {
    String brand;
    double price;
    int batteryLevel;

    public Phone(String brand, double price, int batteryLevel) {
        this.brand = brand;
        this.price = price;
        this.batteryLevel = batteryLevel;
    }

    public void call(String personName){
        if(this.batteryLevel > 10){
            System.out.println("正在给" + personName + "打电话...");
            this.batteryLevel -= 5;
        } else if(this.batteryLevel < 10){
            System.out.println("电量不足，无法拨打电话！");
        }
    }

    public void charge(){
        this.batteryLevel = 100;
    }

    public void showStatus(){
        System.out.println("----- 当前手机状态 -----");
        System.out.println("品牌：" + this.brand);
        System.out.println("价格：" + this.price);
        System.out.println("剩余电量：" + this.batteryLevel);
        System.out.println("-------------------");
    }
}
