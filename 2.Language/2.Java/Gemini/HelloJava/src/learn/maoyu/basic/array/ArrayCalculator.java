package learn.maoyu.basic.array;

public class ArrayCalculator {
    public static void main(String[] args) {
        double[] prices = {12.5, 30.0, 9.99, 105.5, 60.0};
        double total = 0;
        for(double price : prices)
        {
            total += price;
        }
        System.out.println("TOTAL: " + total);
        System.out.println("AVE: " + (total/prices.length));
    }
}
