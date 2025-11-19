package learn.maoyu.basic.method;

public class ArrayHelper {
    public static void main(String[] args) {
        int[] arr = {5, 15, 99, 42};
        System.out.println(findMax(arr));
    }
    public static int findMax(int[] arr) {
        int max = arr[0];
        for (int i = 1; i < arr.length; i++) {
            if (arr[i] > max) {
                max = arr[i];
            }
        }
        return max;
    }
}
