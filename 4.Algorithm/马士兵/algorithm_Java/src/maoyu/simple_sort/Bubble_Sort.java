package maoyu.simple_sort;

import java.util.Arrays;

public class Bubble_Sort {
    /**
     * 对整型数组进行冒泡排序（升序），并进行优化。
     *
     * @param arr 待排序的数组
     */
    public void sort(int[] arr) {
        int n = arr.length;
        boolean swapped;

        // 外层循环控制排序的轮数
        for (int i = 0; i < n - 1; i++) {
            swapped = false;

            // 内层循环进行相邻元素的比较和交换
            for (int j = 0; j < n - i - 1; j++) {
                if (arr[j] > arr[j + 1]) {
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                    swapped = true;
                }
            }
            // 如果在本轮没有发生交换，则说明数组已经有序
            if (!swapped) {
                break;
            }
        }
    }
    // 示例
    public static void main(String[] args) {
        Bubble_Sort sorter = new Bubble_Sort();
        int[] myList = {64, 34, 25, 12, 22, 11, 90};
        sorter.sort(myList);
        System.out.println("排序后的数组: " + Arrays.toString(myList));
    }
}
