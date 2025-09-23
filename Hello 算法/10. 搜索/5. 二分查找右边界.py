def binary_search_left_edge(nums: list[int], target: int) -> int:
    i, j = 0, len(nums) - 1
    while i <= j:
        m = (i + j) // 2
        if nums[m] < target:
            i = m + 1
        elif nums[m] > target:
            j = m - 1
        else:
            i = m + 1
    if i == len(nums) or nums[i] != target:
        return -1
    return i

nums1 = [1, 2, 3, 6, 6, 6, 6 ,6, 8, 12, 15, 23, 26, 31,35]
target1 = 7
print(binary_search_left_edge(nums1, target1))