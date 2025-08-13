from collections import deque   # deque(双向队列)

# 1. 定义二叉树节点
class TreeNode:
    def __init__(self, val=0, left=None, right=None):
        self.val = val
        self.left = left
        self.right = right

    def __repr__(self):
        # 让节点打印更易读
        return f"TreeNode({self.val})"

# 2. 实现遍历函数
class BinaryTreeTraversal:
    # 前序遍历 （根 -> 左 -> 右）
    def preorder_traversal(self, root: TreeNode) -> list:
        result = []
        def dfs(node):
            if not node:
                return
            result.append(node.val)
            dfs(node.left)
            dfs(node.right)
        dfs(root)
        return result

    # 中序遍历 (左 -> 根 -> 右)
    def inorder_traversal(self, root: TreeNode) -> list:
        result = []
        def dfs(node):
            if not node:
                return
            dfs(node.left)
            result.append(node.val)
            dfs(node.right)
        dfs(root)
        return result

    # 后序遍历 (左 -> 右 -> 根)
    def postorder_traversal(self, root: TreeNode) -> list:
        result = []
        def dfs(node):
            if not node:
                return
            dfs(node.left)
            dfs(node.right)
            result.append(node.val)
        dfs(root)
        return result

    # 层序遍历 （BFS）
    def levelorder_traversal(self, root: TreeNode) -> list:
        if not root:
            return []
        result = []
        queue = deque([root])
        while queue:
            node = queue.popleft()
            result.append(node.val)
            if node.left:
                queue.append(node.left)
            if node.right:
                queue.append(node.right)
        return result

# 3. 使用示例
if __name__ == '__main__':
    print("--- 1. Binary Tree Example ---")
    # 手动构建一棵树:
    #      1
    #     / \
    #    2   3
    #   / \
    #  4   5
    root = TreeNode(1)
    root.left = TreeNode(2)
    root.right = TreeNode(3)
    root.left.left = TreeNode(4)
    root.left.right = TreeNode(5)

    traversals = BinaryTreeTraversal()
    print(f"Pre-order (根左右): {traversals.preorder_traversal(root)}")
    print(f"In-order (左根右): {traversals.inorder_traversal(root)}")
    print(f"Post-order (左右根): {traversals.postorder_traversal(root)}")
    print(f"Level-order (层序): {traversals.levelorder_traversal(root)}")
    print("-" * 20 + "\n")