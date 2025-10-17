def solution(nums, k):
    count = len(nums)
    new_nums = []
    for i in range(count - k, count):
        new_nums.append(nums[i])
    for j in range(count - k):
        new_nums.append(nums[j])
    print(new_nums)



nums1 = [1, 2, 3, 4, 5, 6, 7, 8, 9, 10]
k1 = 3

solution(nums1, k1)