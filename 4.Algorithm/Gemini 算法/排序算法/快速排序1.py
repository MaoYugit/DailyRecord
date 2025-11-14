def partition(arr, low, high):
    """
        分区函数。
        它将数组的最后一个元素作为基准 (pivot)，
        然后将所有小于基准的元素放在基准的左边，
        所有大于基准的元素放在右边。

        参数:
        arr -- 需要排序的数组
        low -- 子数组的起始索引
        high -- 子数组的结束索引

        返回:
        基准元素最终所在位置的索引
    """
    # 选择最后一个元素作为基准
    pivot = arr[high]

    # i 是指向较小元素区域右边界的指针
    i = low - 1

    # 遍历从 low 到 high-1 的所有元素
    for j in range(low, high):
        # 如果当前元素小于或等于基准
        if arr[j] <= pivot:
            # 将 i 指针向右移动
            i += 1
            # 交换 arr[i] 和 arr[j]，将较小元素放到左边区域
            arr[i], arr[j] = arr[j], arr[i]

    # 将基准元素放到正确的位置（i+1）
    # 此时 i 的位置是最后一个小于基准的元素的位置
    arr[i+1], arr[high] = arr[high], arr[i+1]

    # 返回基准的索引
    return i + 1

def quick_sort(arr, low, high):
    """
        快速排序的主函数，实现了分治思想。

        参数:
        arr -- 需要排序的数组
        low -- 子数组的起始索引
        high -- 子数组的结束索引
    """
    if low < high:
        # pi 是分区后基准元素的索引
        pi = partition(arr, low, high)

        # 递归地对基准左边的子数组进行排序
        quick_sort(arr, low, pi - 1)

        # 递归地对基准右边的子数组进行排序
        quick_sort(arr, pi + 1, high)

# --- 使用示例 ---
my_list = [8, 9, 2, 1, 5, 6, 2, 8, 11, 3]
quick_sort(my_list, 0, len(my_list) - 1)
print(my_list)





# 0 1 2 3 4 5 6 7 8  9
# 8 9 2 1 5 6 2 8 11 3
# 初始情况：
# low = 0; high = 9; pivot = 3; i = -1
# 开始循环
# j = 0; 8 不变
# j = 1; 9 不变
# j = 2; 2 小于基准3; i = i + 1 = 0; 交换：2 9 8 1 5 6 2 8 11 3
# j = 3; 1 小于基准3; i = i + 1 = 1; 交换：2 1 8 9 5 6 2 8 11 3
# j = 4; 5 不变
# j = 5; 6 不变
# j = 6; 2 小于基准3; i = i + 1 = 2; 交换：2 1 2 9 5 6 8 11 3
# j = 7; 8 不变
# j = 8; 11 不变
# 循环结束
# i = i + 1 = 3; 交换： 2 1 2 3 9 5 6 8 11
