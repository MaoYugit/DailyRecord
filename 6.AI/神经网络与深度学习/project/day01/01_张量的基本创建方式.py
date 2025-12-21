"""
torch.tensor 根据指定数据创建张量
torch.Tensor 根据形状创建张量 也可用来创建指定数据的张量
torch.IntTensor torch.FloatTensor torch.DoubleTensor torch.LongTensor 创建指定类型的张量
"""
import torch
import numpy as np

def dm01():
    t1 = torch.tensor(10)
    print(f"t1: {t1}")

    print("-" * 30)

    data = [[1,2,3],[4,5,6]]
    t2 = torch.Tensor(data)
    print(f"t2: {t2}")

    print("-" * 30)

    data = np.random.randint(0, 10, size=(2, 3))
    t3 = torch.Tensor(data)
    print(f"t3: {t3}")

    print("-" * 30)


if __name__ == '__main__':
    dm01()
