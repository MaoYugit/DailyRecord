def binary_search(nums: list[int], target: int) -> int:
    i, j = 0, len(nums) - 1
    while i <= j:
        m = (i + j) //2
        if nums[m] < target:
            i = m + 1
        elif nums[m] > target:
            j = m - 1
        else:
            return m
    return -1

nums1 = [1, 3, 6, 8, 12, 15, 23, 26, 31,35]
target1 = 6
print(binary_search(nums1, target1))