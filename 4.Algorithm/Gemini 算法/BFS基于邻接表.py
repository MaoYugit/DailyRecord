# 无向图
# 节点数：6 (编号 0 到 5)
# 边 (无向): (0,1), (0,2), (1,3), (1,4), (2,4), (2,5), (3,4), (4,5)
# 起点：0


# 图的表示（字典）
adj = {
    0: [1, 2],
    1: [0, 3, 4],
    2: [0, 4, 5],
    3: [1, 4],
    4: [1, 2, 3, 5],
    5: [2, 4]
}

from collections import deque # Python中，双端队列deque是实现队列的高效选择

def bfs_adjacency_list(adj, start_node):
    # 步骤 1: 初始化
    queue = deque()  # 装备1: 任务白板 (队列)
    visited = set()  # 装备2: 全局地图 (集合)

    # 步骤 2: 出发
    queue.append(start_node)
    visited.add(start_node) # 黄金准则：入队即标记！

    print("BFS Traversal Order:")

    # 步骤 3: 主循环
    while queue: # 只要任务白板上还有地点
        # a. 从队头取出一个节点 u
        u = queue.popleft()

        # b. 处理节点 u
        print(u, end=' ')

        # c. 遍历 u 的所有邻居节点 v
        if u in adj: # 确保节点在邻接表中有定义
            for v in adj[u]:
                # i. 如果 v 未被访问过
                if v not in visited:
                    # - 将 v 标记为已访问
                    visited.add(v)
                    # - 将 v 加入 Q 的尾部
                    queue.append(v)

    print("\nBFS finished.")

bfs_adjacency_list(adj, 0)
