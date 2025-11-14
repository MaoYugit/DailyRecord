import random


def partition_random(arr, low, high):
    """
    使用随机基准的分区函数。
    """
    # 随机选择一个索引
    rand_pivot_index = random.randint(low, high)

    # 将随机选择的基准与最后一个元素交换
    arr[rand_pivot_index], arr[high] = arr[high], arr[rand_pivot_index]

    # 之后的分区逻辑与基础版本完全相同
    return partition(arr, low, high)


def quick_sort_random(arr, low, high):
    """
    使用随机基准的快速排序主函数。
    """
    if low < high:
        # 使用随机化的分区函数
        pi = partition_random(arr, low, high)

        quick_sort_random(arr, low, pi - 1)
        quick_sort_random(arr, pi + 1, high)


# --- 使用示例 ---

# partition 函数与版本一中的完全相同
def partition(arr, low, high):
    pivot = arr[high]
    i = low - 1
    for j in range(low, high):
        if arr[j] <= pivot:
            i += 1
            arr[i], arr[j] = arr[j], arr[i]
    arr[i + 1], arr[high] = arr[high], arr[i + 1]
    return i + 1


if __name__ == "__main__":
    my_list_random = [8, 3, 1, 7, 0, 10, 2]
    print("\n--- 随机基准版本 ---")
    print("原始数组:", my_list_random)

    # 调用随机化快速排序
    quick_sort_random(my_list_random, 0, len(my_list_random) - 1)

    print("排序后的数组:", my_list_random)

    # 测试已排序的数组（现在不会是坏情况了）
    sorted_list_random = [1, 2, 3, 4, 5, 6]
    print("\n测试已排序数组:", sorted_list_random)
    quick_sort_random(sorted_list_random, 0, len(sorted_list_random) - 1)
    print("排序结果:", sorted_list_random)
#
# my_list = [8, 9, 2, 1, 5, 6, 2, 8, 11, 3]
# quick_sort(my_list, 0, len(my_list) - 1)
# print(my_list)