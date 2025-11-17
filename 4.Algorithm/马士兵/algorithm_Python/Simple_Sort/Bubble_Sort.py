def bubble_sort(arr):
    """
    对列表进行冒泡排序（升序），并进行优化。
    如果在某一轮中没有发生任何元素交换，说明列表已经有序，可以提前结束。

    :param arr: 待排序的列表
    """
    n = len(arr)
    # 外层循环控制排序的轮数
    for i in range(n - 1):
        # 标志位，用于标记本轮是否发生了交换
        swapped = False
        # 内层循环进行相邻元素的比较和交换
        for j in range(0, n - i - 1):
            if arr[j] > arr[j + 1]:
                # 交换元素
                arr[j], arr[j + 1] = arr[j + 1], arr[j]
                swapped = True

        # 如果在本轮没有发生交换，则说明列表已经有序
        if not swapped:
            break


# 示例
if __name__ == "__main__":
    my_list = [64, 34, 25, 12, 22, 11, 90]
    bubble_sort(my_list)
    print("排序后的数组:", my_list)