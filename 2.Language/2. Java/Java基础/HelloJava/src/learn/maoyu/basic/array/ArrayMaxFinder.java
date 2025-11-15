package learn.maoyu.basic.array;

public class ArrayMaxFinder {
    public static void main(String[] args) {
        int[] numbers = {5, 15, 2, 99, 38, -10, 0, 42};
        int max = numbers[0];
        for (int i = 1; i < numbers.length; i++) {
            if (numbers[i] > max) {
                max = numbers[i];
            }
        }

        System.out.println("max: " + max);
    }
}
