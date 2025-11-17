package learn.maoyu.basic.operators;

public class ShoppingReceipt {
    public static void main(String[] args) {
        double T_shirt = 245.0;
        double shoes = 570.0;
        double tennis = 60.5;
        double bat = 320;
        double discount = 0.8;
        double cost = T_shirt + shoes + tennis + bat;
        double cost_real = cost * discount;
        double cash = 1500;
        double balance = cash - cost_real;

        System.out.println("商品件数:" + 4);
        System.out.println("总金额:" + cost);
        System.out.println("折扣:" + (cost - cost_real));
        System.out.println("实付金额:" + cost);
        System.out.println("找零:" + balance);
    }
}
