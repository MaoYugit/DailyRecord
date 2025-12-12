from collections import deque
from typing import List


class Solution:
    def updateMatrix(self, mat: List[List[int]]) -> List[List[int]]:
        rows, cols = len(mat), len(mat[0])
        queue = deque()

        # 1. 初始化
        # 遍历整个矩阵：
        # - 如果是 0，加入队列（它是传染源/起点）
        # - 如果是 1，标记为 -1（表示未被访问过/待更新），避免重复访问
        for r in range(rows):
            for c in range(cols):
                if mat[r][c] == 0:
                    queue.append((r, c))
                else:
                    mat[r][c] = -1  # 标记为未访问

        directions = [(0, 1), (0, -1), (1, 0), (-1, 0)]

        # 2. 开始 BFS
        while queue:
            r, c = queue.popleft()

            for dr, dc in directions:
                nr, nc = r + dr, c + dc

                # 3. 边界检查 + 访问状态检查
                # 只有当邻居是 -1 (未访问) 时，才去更新它
                if 0 <= nr < rows and 0 <= nc < cols and mat[nr][nc] == -1:
                    # 更新距离：等于来源点的距离 + 1
                    mat[nr][nc] = mat[r][c] + 1
                    # 加入队列，继续向外扩散
                    queue.append((nr, nc))

        return mat