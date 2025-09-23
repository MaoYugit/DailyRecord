import numpy as np

# -- 加法与数乘 --
# 假设我们有两个用户的特征向量：[网页浏览时长(分钟)，购买次数]
user_stats_1 = np.array([50, 3])
user_stats_2 = np.array([20, 8])

# 1. 向量加法: 得到总计的统计数据
total_stats = user_stats_1 + user_stats_2
print(f"向量相加: {user_stats_1} + {user_stats_2} = {total_stats}")

print("-" * 30)

# 2. 标量乘法: 假设我们要预测一周的浏览时长
weekly_browsing_1 = user_stats_1[0] * 7
print(f"用户1的周浏览时长预测: {user_stats_1[0]} * 7 = {weekly_browsing_1}")

print("-" * 30)

# -- 点积 --
# 假设我们有三个用户的电影评分向量：[科幻, 动作, 爱情]
# 评分范围1-5
user_A = np.array([5, 4, 1]) # 喜欢科幻、动作
user_B = np.array([4, 5, 0]) # 和A类似
user_C = np.array([1, 0, 5]) # 喜欢爱情片

# 计算A和B的点积
dot_AB = np.dot(user_A, user_B)
print(f"用户A和B的点积: {dot_AB}") # 期待一个比较大的正数

# 计算A和C的点积
dot_AC = np.dot(user_A, user_C)
print(f"用户A和C的点积: {dot_AC}") # 期待一个比较小的数

# 我们可以用 @ 符号进行更简洁的点积/矩阵乘法运算
dot_BC = user_B @ user_C
print(f"用户B和C的点积 (用@): {dot_BC}")

print("结论：点积越大，代表用户品味越相似。推荐系统可以据此为A推荐B喜欢的电影。\n")

student_A = np.array([8, 9, 2])
student_B = np.array([3, 4, 9])

gain = np.array([2, 3, 1])

student_A_new = student_A + gain
student_B_new = student_B + gain

print(f"student_A学习数据科学课程包后，新的兴趣向量是：{student_A_new}")
print(f"student_B学习数据科学课程包后，新的兴趣向量是：{student_B_new}")

# 创建副本以避免修改原始向量
student_A_updated_v1 = student_A.copy()
student_B_updated_v1 = student_B.copy()

# “编程”在索引1的位置
student_A_updated_v1[1] = student_A_updated_v1[1] * 2
student_B_updated_v1[1] = student_B_updated_v1[1] * 2

print(f"方法一：学生A更新后的向量: {student_A_updated_v1}") # 结果应为 [8, 18, 2]
print(f"方法一：学生B更新后的向量: {student_B_updated_v1}") # 结果应为 [3, 8, 9]

dot_AB = student_A @ student_B
print(f"学生A和学生B原始兴趣向量的点积:{dot_AB}")