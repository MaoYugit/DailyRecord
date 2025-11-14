import queue
from collections import deque

def bfs_grid_shortest_path(grid):
    if not grid or not grid[0]:
        return -1

    rows, cols = len(grid), len(grid[0])

    # 找到起点
    start_pos = None
    for r in range(rows):
        for c in range(cols):
            if grid[r][c] == 'S':
                start_pos = (r, c)
                break
        if start_pos:
            break

    if not start_pos:
        return -1   # 没有起点

    # 步骤 1: 初始化
    # 队列中存 (行, 列, 距离)
    queue = deque([(start_pos[0], start_pos[1], 0)])
    visited = {start_pos}  # visited 存坐标元组

    # 定义邻居方向: 上, 下, 左, 右
    directions = [(-1, 0), (1, 0), (0, -1), (0, 1)]

    # 步骤 3: 主循环
    while queue:
        # a. 取出当前节点信息
        r, c, distance = queue.popleft()

        # b. 处理节点 (检查是否为终点)
        if grid[r][c] == 'E':
            return distance  # 找到了！返回最短距离

        # c. 遍历所有邻居
        for dr, dc in directions:
            new_r, new_c = r + dr, c + dc
            # 检查邻居是否有效:
            # 1. 是否在网格内?
            # 2. 是否是墙壁?
            # 3. 是否已经访问过?
            if 0 <= new_r < rows and 0 <= new_c < cols and \
                    grid[new_r][new_c] != 1 and \
                    (new_r, new_c) not in visited:
                # i. 标记并入队
                visited.add((new_r, new_c))
                queue.append((new_r, new_c, distance + 1))

    return -1  # 队列为空还没找到，说明终点不可达


# 定义我们的示例迷宫
# 注意 'S' 和 'E' 是字符串，而 0 和 1 是整数
maze_grid = [
    ['S',  0,  0,  1],
    [ 1,   0,  1,  0],
    [ 0,   0,  0, 'E'],
    [ 0,   1,  1,  0]
]

# 2. 调用函数，并把迷宫传进去
shortest_path_length = bfs_grid_shortest_path(maze_grid)

# 3. 打印结果
if shortest_path_length != -1:
    print(f"找到了最短路径！长度为: {shortest_path_length}")
else:
    print("无法从起点到达终点。")