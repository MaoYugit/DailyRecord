import numpy as np
A = np.array([
    [1, 2, 3],
    [4, 5, 6]
]) # 形状 (2, 3)

B = np.array([
    [7, 8],
    [9, 10],
    [11, 12]
]) # 形状 (3, 2)

C = A @ B
print("A @ B 的结果:")
print(C)
print(f"结果的形状: {C.shape}")

print("-" * 30)

# 尝试计算 B @ A
# B的形状是(3, 2), A的形状是(2, 3) -> 中间维度2匹配，可以计算
C_reversed = B @ A
print("B @ A 的结果:")
print(C_reversed)
print(f"结果的形状: {C_reversed.shape}") # 结果是 (3, 3) 的矩阵
print("结论：A @ B 和 B @ A 的结果完全不同！\n")

print("-" * 30)


# 尝试计算 A @ A (形状 (2,3) @ (2,3) -> 3和2不匹配)
try:
    A @ A
except ValueError as e:
    print(f"\n尝试计算 A @ A 失败，错误信息: {e}")