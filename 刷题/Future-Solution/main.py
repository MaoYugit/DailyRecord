import collections


def solve():
    """
    矩阵扩散问题的主函数
    """
    try:
        # 1. 获取并解析输入
        # 输入格式为 "m,n,r1,c1,r2,c2"
        m, n, r1, c1, r2, c2 = map(int, input().split(','))

        # 2. 准备工作
        # 创建一个队列用于BFS
        # collections.deque 是一个双端队列，从左侧弹出(popleft)是 O(1) 操作，比 list 的 pop(0) 高效得多
        queue = collections.deque()

        # 创建一个 time_grid 矩阵来记录每个单元格被扩散到的时间
        # 初始化为 -1 表示所有单元格都未被访问过
        time_grid = [[-1 for _ in range(n)] for _ in range(m)]

        # 最终答案，即所有格子都变成1所需的最大时间
        max_time = 0

        # 3. 初始化多源BFS
        # 将两个初始扩散点加入队列，并设置它们的扩散时间为0

        # 初始点1
        if 0 <= r1 < m and 0 <= c1 < n:
            queue.append((r1, c1))
            time_grid[r1][c1] = 0

        # 初始点2 (注意，两个点可能相同，但我们的逻辑也能正确处理)
        if time_grid[r2][c2] == -1:  # 避免重复添加同一个点
            if 0 <= r2 < m and 0 <= c2 < n:
                queue.append((r2, c2))
                time_grid[r2][c2] = 0

        # 定义四个方向的移动：上, 下, 左, 右
        directions = [(-1, 0), (1, 0), (0, -1), (0, 1)]

        # 4. 开始BFS循环
        while queue:
            # 从队列左侧取出一个点 (r, c)
            r, c = queue.popleft()

            # 当前点的时间
            current_time = time_grid[r][c]

            # 更新全局最大时间。因为BFS是按层遍历，最后出队的点的时间就是最长时间
            max_time = current_time

            # 遍历该点的四个邻居
            for dr, dc in directions:
                nr, nc = r + dr, c + dc

                # 检查邻居的有效性：
                # 1. 是否在矩阵边界内
                # 2. 是否还未被访问过 (time_grid[nr][nc] == -1)
                if 0 <= nr < m and 0 <= nc < n and time_grid[nr][nc] == -1:
                    # 更新邻居的时间
                    time_grid[nr][nc] = current_time + 1
                    # 将邻居加入队列，等待处理
                    queue.append((nr, nc))

        # 5. 输出结果
        print(max_time)

    except (ValueError, IndexError) as e:
        # 处理可能的输入格式错误或索引越界
        print(f"输入有误或处理出错: {e}")


# 调用函数执行
solve()