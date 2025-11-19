package learn.maoyu.basic.method;

public class NumberChecker {
    public static void main(String[] args) {
        checkNumber(56);
    }
    public static void checkNumber(int number){
        if(number % 2 == 0){
            System.out.println(number + " is an even number");
        }
        else{
            System.out.println(number + " is an odd number");
        }
    }
}
