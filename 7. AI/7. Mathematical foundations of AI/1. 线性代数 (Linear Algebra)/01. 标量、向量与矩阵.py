import numpy as np

# 1. 创建一个标量 (在Python中，它只是一个普通的变量)
scalar = 25
print(f"这是一个标量: {scalar}")
print("-" * 20)

# 2. 创建一个向量 (代表我们的用户A)
# 我们用 np.array() 来创建一个NumPy数组
user_a = np.array([25, 60000, 15])
print(f"这是一个向量 (代表用户A): {user_a}")
# 我们可以查看它的“形状”或维度
print(f"向量的形状: {user_a.shape}") # (3,) 表示这是一个包含3个元素的一维数组
print("-" * 20)

# 3. 创建一个矩阵 (代表我们的用户数据集)
# 我们可以通过一个列表的列表来创建
dataset = np.array([
    [25, 60000, 15],  # 用户A
    [35, 80000, 5],   # 用户B
    [22, 35000, 20]   # 用户C
])
print("这是一个矩阵 (代表我们的数据集):")
print(dataset)
# 查看矩阵的形状
print(f"矩阵的形状: {dataset.shape}") # (3, 3) 表示这是一个3行3列的矩阵