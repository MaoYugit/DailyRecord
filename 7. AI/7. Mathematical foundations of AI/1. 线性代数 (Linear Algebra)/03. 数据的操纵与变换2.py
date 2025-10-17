import numpy as np
import matplotlib.pyplot as plt


def plot_vectors(vectors, colors, labels, title):
    """一个好用的函数，用于绘制向量"""
    plt.figure(figsize=(6, 6))  # 设置画布的大小为 6x6 英寸
    # 在 y=0 的位置画一条horizontal line（水平线），这就是 x 轴
    plt.axhline(0, color='black', linewidth=0.5)
    # 在 x=0 的位置画一条vertical line（垂直线），这就是 y 轴。
    plt.axvline(0, color='black', linewidth=0.5)
    # 在背景中添加网格线 (True)，并设置为虚线 (--)
    plt.grid(True, linestyle='--')

    for i, vec in enumerate(vectors):
        # 绘制从原点出发的箭头
        # 0, 0: 这是箭头的起始点 x, y 坐标。我们让所有向量都从原点 (0,0) 开始
        # vec[0], vec[1]: 这是箭头的分量，即从起始点开始，在 x 方向上走多远 (vec[0])，在 y 方向上走多远 (vec[1])。这决定了箭头的指向和长度。
        # label=labels[i]: 给当前这个箭头赋予一个标签。同样用索引 i 从 labels 列表中找到对应的名字。这个标签是给后面的 plt.legend() 使用的
        plt.quiver(0, 0, vec[0], vec[1], angles='xy', scale_units='xy', scale=1, color=colors[i], label=labels[i])

    # 步骤 4: 自动调整坐标轴范围
    # np.abs(vectors): 计算所有向量坐标的绝对值。
    # np.max(...): 找到所有绝对值中的最大值。
    #  1.2: 将这个最大值乘以 1.2，是为了在图形的边缘留出 20% 的空白，这样最长的箭头也不会顶到边框，看起来更美观。
    max_val = np.max(np.abs(vectors)) * 1.2
    # plt.xlim(...) 和 plt.ylim(...): 设置 x 轴和 y 轴的显示范围，确保所有我们画的向量都能被完整地看到。
    plt.xlim(-max_val, max_val)
    plt.ylim(-max_val, max_val)
    # gca 意为 "Get Current Axes"（获取当前坐标轴）。.set_aspect('equal') 强制要求 x 轴和 y 轴的单位刻度长度相等。这保证了图形不会被拉伸变形，旋转45度就是真正的45度，一个圆不会被压成椭圆。
    plt.gca().set_aspect('equal', adjustable='box')

    plt.title(title, fontsize=14)
    # 自动生成图例。它会找到所有带 label 的元素（就是我们画的那些箭头），然后在一个小框里显示出哪个颜色对应哪个标签。
    plt.legend()
    plt.show()


# --- 1. 缩放 (Scaling) ---
v = np.array([2, 1])
scaling_matrix = np.array([
    [1.5, 0],
    [0, 0.5]
])

transformed_v = scaling_matrix @ v

print("--- 1. 缩放 (Scaling) ---")
print(f"原始向量 v: {v}")
print(f"缩放矩阵 S:\n{scaling_matrix}")
print(f"变换后向量 S @ v: {transformed_v}")

plot_vectors(
    [v, transformed_v],
    ['blue', 'red'],
    [f'Original v={v}', f'Transformed v={transformed_v}'],
    "Scaling Transformation"
)