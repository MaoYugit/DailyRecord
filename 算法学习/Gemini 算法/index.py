def bubble_sort(nums):
    n = len(nums)
    for i in range(0, n):
        for j in range(0, n - i - 1):
            if nums[j] > nums[j + 1]:
                nums[j], nums[j + 1] = nums[j + 1], nums[j]
    return nums

arr = [1, 2, 5, 4, 3, 7, 6, 12, 10, 6, 5, 5, 4, 1]

print(bubble_sort(arr))