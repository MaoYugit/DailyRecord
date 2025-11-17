package maoyu.simple_sort;

import java.util.Arrays;

public class Selection_Sort {

    /**
     * 对整型数组进行选择排序（升序）
     *
     * @param arr 待排序的数组
     */
    public void sort(int[] arr) {
        int n = arr.length;
        // 遍历数组中的每一个元素，最后一个元素无需遍历
        for (int i = 0; i < n - 1; i++) {
            // 假设当前索引的元素是未排序部分的最小值
            int minIndex = i;

            // 在未排序部分中查找最小元素的索引
            for (int j = i + 1; j < n; j++){
                if (arr[j] < arr[minIndex]) {
                    minIndex = j;
                }
            }
            // 将最小元素与当前索引的元素交换位置
            int temp = arr[i];
            arr[i] = arr[minIndex];
            arr[minIndex] = temp;
        }
    }

    // 示例
    public static void main(String[] args) {
        Selection_Sort sorter = new Selection_Sort();
        int[] myList = {64, 25, 12, 22, 11, 0};
        sorter.sort(myList);
        System.out.println("排序后的数组: " + Arrays.toString(myList));
    }
}
