def selection_sort(arr):
    """
        对列表进行选择排序（升序）

        :param arr: 待排序的列表
    """
    # 遍历列表中的每一个元素
    for i in range(len(arr) - 1):
        # 假设当前位置的元素是未排序部分的最小值
        min_index = i

        # 遍历未排序部分，寻找真正的最小值
        for j in range(i + 1, len(arr)):
            if arr[j] < arr[min_index]:
                min_index = j

        # 如果找到了一个更小的元素，则将其与当前位置的元素交换
        if min_index != i:
            arr[i], arr[min_index] = arr[min_index], arr[i]


# 示例
if __name__ == "__main__":
    my_list = [64, 25, 12, 22, 11, 0, 99]
    selection_sort(my_list)
    print("排序后的数组:", my_list)