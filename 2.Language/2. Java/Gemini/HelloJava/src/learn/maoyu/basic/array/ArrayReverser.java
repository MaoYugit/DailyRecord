package learn.maoyu.basic.array;

import java.util.Arrays;

public class ArrayReverser {
    public static void main(String[] args) {
        String[] letters = {"a", "b", "c", "d", "e"};
        int start = 0;
        int end = letters.length - 1;
        while (start <= end) {
            String tem_letter = letters[start];
            letters[start] = letters[end];
            letters[end] = tem_letter;
            start++;
            end--;
        }
        System.out.println(Arrays.toString(letters));
    }
}
