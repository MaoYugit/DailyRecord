import torch

# 1. 准备数据 (假设 y = 3x + 0.8)
x = torch.linspace(0, 1, 100).reshape(-1, 1)
y_true = 3 * x + 0.8 + torch.randn(100, 1) * 0.1  # 加点噪声

# 2. 初始化参数 (随机初始化)
w = torch.randn(1, requires_grad=True)
b = torch.zeros(1, requires_grad=True)

learning_rate = 0.1

# 3. 训练循环
for i in range(500):
    # 前向传播：预测值
    y_pred = x * w + b

    # 计算损失 (均方误差 MSE)
    loss = torch.mean((y_pred - y_true) ** 2)

    # 反向传播：计算梯度
    loss.backward()

    # 更新参数 (不需要求导的过程放在 no_grad 里)
    with torch.no_grad():
        w -= learning_rate * w.grad
        b -= learning_rate * b.grad

        # 梯度清零 (PyTorch 默认会累加梯度，必须手动清零)
        w.grad.zero_()
        b.grad.zero_()

    if i % 50 == 0:
        print(f"Epoch {i}, Loss: {loss.item():.4f}, w: {w.item():.2f}, b: {b.item():.2f}")

print(f"\n训练结束！最终结果：w={w.item():.2f}, b={b.item():.2f}")