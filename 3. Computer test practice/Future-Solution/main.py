class ListNode:
    """
    定义一个链表节点类 (一节车厢)
    """
    def __init__(self, val=0, next=None):
        self.val = val    # 存储本节点的数据 (车厢里的货物)
        self.next = next  # 指向下一个节点的引用 (连接下一节车厢的挂钩)

    def __repr__(self):
        # 这个方法是为了方便我们打印节点时看到它的值
        return f"Node({self.val})"

node1 = ListNode(10)
node2 = ListNode(20)
node3 = ListNode(30)

node1.next = node2
node2.next = node3

head = node1

def traverse_list(head: ListNode):
    print("start...")
    current = head
    while current is not None:
        print(current.val, end="->")
        current = current.next
    print("None")

traverse_list(head)