package learn.maoyu.oop.classlearn;

public class PhoneStore {
    public static void main(String[] args) {
        Phone iphone17 = new Phone("iphone", 7999, 23);

        iphone17.call("HUAWEI");
        iphone17.call("HUAWEI");
        iphone17.call("HUAWEI");
        iphone17.call("HUAWEI");

        iphone17.charge();
        iphone17.call("HUAWEI");
        iphone17.call("HUAWEI");

        iphone17.showStatus();
    }
}
