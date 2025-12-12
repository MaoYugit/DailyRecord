import sys


# 1. 定义函数（保持你习惯的写法，不用 class）
def search(nums, target) -> int:
    left = 0
    right = len(nums) - 1

    while left <= right:
        mid = left + (right - left) // 2
        if nums[mid] == target:
            return mid
        elif nums[mid] < target:
            left = mid + 1
        else:
            right = mid - 1
    return -1


# 2. 主逻辑循环
if __name__ == "__main__":
    # 使用 sys.stdin 读取所有行，或者用 while 循环读取
    # 这里演示一种最通用的读取方式：

    while True:
        try:
            # 尝试读取第一行（数组）
            line1 = sys.stdin.readline()
            if not line1:
                break  # 读不到数据了，退出循环

            line1 = line1.strip()
            if not line1:  # 处理空行的情况
                continue

            nums = list(map(int, line1.split()))

            # 尝试读取第二行（target）
            line2 = sys.stdin.readline().strip()
            target = int(line2)

            # 调用函数并打印
            result = search(nums, target)
            print(result)

        except EOFError:
            break