import bisect
nums = [1, 2, 2, 2, 3, 6, 5 ,4 ]
nums.sort()
bisect.bisect_right(nums, 2)
print(bisect.bisect_left(nums, 4))
print(nums)