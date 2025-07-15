### **第三章：链表 (Linked Lists)**

在第二章中，我们确定了数组的核心弱点：在开头或中间进行插入和删除操作时，由于需要移动大量元素以维持内存的连续性，其时间复杂度为 O(n)。链表是一种旨在克服这一特定缺陷的数据结构。

#### **3.1 链表的定义与特性：非连续内存存储与节点结构**

**定义**：链表 (Linked List) 是一种线性数据结构，它不将元素存储在连续的内存空间中。相反，它由一系列的**节点 (Nodes)** 组成，每个节点在逻辑上连接到下一个节点。

**核心构件：节点 (Node)**
一个链表的基本单位是节点。一个最基本的节点包含两个部分：

1.  **数据 (Data)**：存储该节点所代表的实际值（例如，一个数字、一个字符串或一个更复杂的对象）。
2.  **指针 (Pointer / Reference)**：存储下一个节点在内存中的地址。这个指针将离散的节点“链接”在一起，构成了链表的逻辑顺序。我们称之为 `next` 指针。

**关键特性：非连续内存 (Non-contiguous Memory)**
与数组不同，链表的节点可以在内存中任意分散存储。一个节点可能在地址 `0x1000`，而它的下一个节点可能在地址 `0x5A80`。这种物理上的不连续性是通过每个节点中的 `next` 指针在逻辑上弥补的。

**链表的基本术语:**
*   **头节点 (Head)**：链表的第一个节点。它是访问整个链表的唯一入口点。如果 `head` 为 `null` 或 `None`，则表示链表为空。
*   **尾节点 (Tail)**：链表的最后一个节点。其 `next` 指针指向 `null` 或 `None`，表示链表的结束。

这种结构直接导致了链表与数组的根本性能差异。由于插入或删除一个节点仅需改变其前后节点的指针，而无需移动其他任何节点，因此这些操作在理论上可以非常高效。然而，代价是失去了 O(1) 的随机访问能力，因为我们无法通过计算直接找到第 `i` 个元素，必须从 `head` 开始，沿着 `next` 指针逐个遍历。

#### **3.2 单向链表 (Singly Linked List)**

这是最简单、最常见的链表形式。在单向链表中，每个节点只有一个 `next` 指针，指向其后继节点。你只能从头到尾单向遍历整个链表。

#### **3.6 链表的从零实现 (Python & JS)**

为了真正理解链表，我们必须从零开始构建它。我们将首先定义 `Node` 类，然后定义 `SinglyLinkedList` 类，并实现其核心操作。

##### **Python 实现**

```python
# Step 1: 定义节点类 (Node Class)
class Node:
    """
    单向链表的节点对象
    """
    def __init__(self, data):
        self.data = data  # 存储数据
        self.next = None  # 指向下一个节点的引用，初始化为None

# Step 2: 定义单向链表类 (SinglyLinkedList Class)
class SinglyLinkedList:
    """
    单向链表数据结构
    """
    def __init__(self):
        self.head = None  # 链表的头节点，初始化为None
        self.size = 0     # 链表的长度

    # 操作 1: 在链表末尾添加节点 (Append) - O(n)
    def append(self, data):
        new_node = Node(data)
        if self.head is None:  # 如果链表为空
            self.head = new_node
        else:
            current = self.head
            while current.next is not None: # 遍历到最后一个节点
                current = current.next
            current.next = new_node # 将最后一个节点的next指向新节点
        self.size += 1

    # 操作 2: 在链表开头添加节点 (Prepend) - O(1)
    def prepend(self, data):
        new_node = Node(data)
        new_node.next = self.head # 新节点的next指向当前的头节点
        self.head = new_node      # 更新链表的头节点为新节点
        self.size += 1

    # 操作 3: 按值搜索节点 (Search) - O(n)
    def search(self, target_data):
        current = self.head
        while current:
            if current.data == target_data:
                return current # 返回找到的节点对象
            current = current.next
        return None # 未找到

    # 操作 4: 按值删除节点 (Delete) - O(n)
    def delete(self, target_data):
        if self.head is None:
            return # 空链表，无事可做

        # Case 1: 要删除的是头节点
        if self.head.data == target_data:
            self.head = self.head.next
            self.size -= 1
            return

        # Case 2: 要删除的是中间或尾部节点
        previous = None
        current = self.head
        while current and current.data != target_data:
            previous = current
            current = current.next

        if current is None: # 未找到目标值
            return

        # 找到了，current是目标节点，previous是其前驱节点
        previous.next = current.next
        self.size -= 1
        
    # 辅助操作: 打印链表
    def display(self):
        elements = []
        current = self.head
        while current:
            elements.append(str(current.data))
            current = current.next
        print(" -> ".join(elements) + " -> None")

# --- 使用示例 ---
sll = SinglyLinkedList()
sll.append(10)  # 10 -> None
sll.append(20)  # 10 -> 20 -> None
sll.append(30)  # 10 -> 20 -> 30 -> None
sll.display()

sll.prepend(5)  # 5 -> 10 -> 20 -> 30 -> None
sll.display()

print(f"Node found: {sll.search(20) is not None}") # True

sll.delete(20) # 5 -> 10 -> 30 -> None
sll.display()

sll.delete(5) # 10 -> 30 -> None
sll.display()
```

##### **JavaScript 实现**

```javascript
// Step 1: 定义节点类 (Node Class)
class Node {
    /**
     * 单向链表的节点对象
     */
    constructor(data) {
        this.data = data; // 存储数据
        this.next = null; // 指向下一个节点的引用，初始化为null
    }
}

// Step 2: 定义单向链表类 (SinglyLinkedList Class)
class SinglyLinkedList {
    /**
     * 单向链表数据结构
     */
    constructor() {
        this.head = null; // 链表的头节点，初始化为null
        this.size = 0;    // 链表的长度
    }

    // 操作 1: 在链表末尾添加节点 (Append) - O(n)
    append(data) {
        const newNode = new Node(data);
        if (this.head === null) { // 如果链表为空
            this.head = newNode;
        } else {
            let current = this.head;
            while (current.next !== null) { // 遍历到最后一个节点
                current = current.next;
            }
            current.next = newNode; // 将最后一个节点的next指向新节点
        }
        this.size++;
    }

    // 操作 2: 在链表开头添加节点 (Prepend) - O(1)
    prepend(data) {
        const newNode = new Node(data);
        newNode.next = this.head; // 新节点的next指向当前的头节点
        this.head = newNode;      // 更新链表的头节点为新节点
        this.size++;
    }

    // 操作 3: 按值搜索节点 (Search) - O(n)
    search(targetData) {
        let current = this.head;
        while (current) {
            if (current.data === targetData) {
                return current; // 返回找到的节点对象
            }
            current = current.next;
        }
        return null; // 未找到
    }

    // 操作 4: 按值删除节点 (Delete) - O(n)
    delete(targetData) {
        if (this.head === null) {
            return; // 空链表，无事可做
        }

        // Case 1: 要删除的是头节点
        if (this.head.data === targetData) {
            this.head = this.head.next;
            this.size--;
            return;
        }

        // Case 2: 要删除的是中间或尾部节点
        let previous = null;
        let current = this.head;
        while (current && current.data !== targetData) {
            previous = current;
            current = current.next;
        }
        
        if (current === null) { // 未找到目标值
            return;
        }

        // 找到了，current是目标节点，previous是其前驱节点
        previous.next = current.next;
        this.size--;
    }

    // 辅助操作: 打印链表
    display() {
        const elements = [];
        let current = this.head;
        while (current) {
            elements.push(String(current.data));
            current = current.next;
        }
        console.log(elements.join(" -> ") + " -> null");
    }
}

// --- 使用示例 ---
const sll = new SinglyLinkedList();
sll.append(10);  // 10 -> null
sll.append(20);  // 10 -> 20 -> null
sll.append(30);  // 10 -> 20 -> 30 -> null
sll.display();

sll.prepend(5);  // 5 -> 10 -> 20 -> 30 -> null
sll.display();

console.log(`Node found: ${sll.search(20) !== null}`); // true

sll.delete(20); // 5 -> 10 -> 30 -> null
sll.display();

sll.delete(5); // 10 -> 30 -> null
sll.display();
```

#### **3.5 核心操作及其时间复杂度分析**

| 操作                   | 时间复杂度 | 备注                                                         |
| :--------------------- | :--------- | :----------------------------------------------------------- |
| **按索引访问**         | **O(n)**   | 必须从头节点开始遍历 `i` 次才能到达第 `i` 个节点。           |
| 按值搜索               | O(n)       | 最坏情况需要遍历整个链表。                                   |
| **开头插入 (Prepend)** | **O(1)**   | 核心优势。只需修改 `head` 指针，与链表长度无关。             |
| **末尾插入 (Append)**  | **O(n)**   | 需要遍历到链表末尾才能进行插入。 (注: 可通过维护一个 `tail` 指针优化至O(1)) |
| 中间插入               | O(n)       | 需要 O(i) 的时间遍历到插入点，但插入动作本身是 O(1)。总复杂度为 O(n)。 |
| **开头删除**           | **O(1)**   | 核心优势。只需将 `head` 指向第二个节点。                     |
| 末尾/中间删除          | O(n)       | 需要 O(i) 的时间遍历到待删除节点的前一个节点。删除动作本身是O(1)。总复杂度为 O(n)。 |

---

#### **3.3 双向链表 (Doubly Linked List)**

**定义**：双向链表是单向链表的一种变体。它的每个节点除了包含 `data` 和 `next` 指针外，还包含一个 `prev` (previous) 指针，指向上一个节点。

**Node 结构**:
*   `data`: 存储数据
*   `next`: 指向后继节点
*   `prev`: 指向前驱节点

**优点**:
1.  **双向遍历**：可以从头到尾，也可以从尾到头遍历。
2.  **高效删除**：如果你拥有一个指向待删除节点的引用 `node_to_delete`，你可以在 O(1) 时间内完成删除。因为通过 `node_to_delete.prev` 可以立即找到前驱节点，无需从头遍历。这是它相较于单向链表最大的优势。`node_to_delete.prev.next = node_to_delete.next`。
3.  **高效的逆序操作**。

**缺点**:
1.  **更大的内存开销**：每个节点都需要额外存储一个 `prev` 指针。
2.  **更复杂的实现**：插入和删除操作需要维护 `prev` 和 `next` 两个指针，需要更新更多的引用，更容易出错。例如，在 `A` 和 `B` 之间插入 `X`，需要修改 `A.next`, `B.prev`, `X.prev`, `X.next` 四个指针。

#### **3.4 循环链表 (Circular Linked List)**

**定义**：循环链表是另一种链表变体，它的尾节点的 `next` 指针不指向 `null`，而是指向链表的头节点 `head`。

**结构**:
*   可以是单向循环链表，也可以是双向循环链表。
*   `tail.next = head`

**优点**:
*   可以从任意节点开始，遍历整个链表。
*   适用于需要循环或轮转的应用场景，如操作系统的任务调度（时间片轮转）、某些游戏中的玩家顺序等。

**缺点**:
*   如果处理不当，遍历操作可能会陷入无限循环。需要用特定的节点（通常是 `head`）作为循环的终止条件。

---

#### **3.7 数组与链表的深度对比**

这张表格是本章和上一章最重要的总结。

| 特性                           | 数组 (Array)                       | 链表 (Linked List)                                 |
| :----------------------------- | :--------------------------------- | :------------------------------------------------- |
| **内存结构**                   | **连续内存空间 (Contiguous)**      | **非连续内存空间 (Non-contiguous)**                |
| **随机访问 (Access by index)** | **O(1)**                           | **O(n)**                                           |
| **按值搜索 (Search by value)** | O(n)                               | O(n)                                               |
| **插入 (开头)**                | O(n)                               | **O(1)**                                           |
| **插入 (中间)**                | O(n)                               | O(n) (遍历) + O(1) (操作)                          |
| **插入 (末尾)**                | 摊还 O(1)                          | O(n) (或 O(1) 如果有尾指针)                        |
| **删除 (开头)**                | O(n)                               | **O(1)**                                           |
| **删除 (中间)**                | O(n)                               | O(n) (遍历) + O(1) (操作)                          |
| **删除 (末尾)**                | O(1)                               | O(n) (需要找到前一个节点)                          |
| **内存开销**                   | 精确，无额外开销                   | 每个节点有指针的额外开销                           |
| **缓存友好性**                 | **高** (连续内存，利于CPU缓存预读) | **低** (离散内存，易导致缓存未命中)                |
| **典型应用场景**               | 频繁访问元素，元素数量相对固定     | **频繁插入/删除元素 (尤其是头尾)**，元素数量不确定 |

**本章小结:**

*   你现在理解了链表通过**节点**和**指针**在**非连续内存**中组织数据的核心思想。
*   你亲手实现了单向链表的创建和核心操作，并能分析其时间复杂度。
*   你掌握了链表与数组在性能上的根本差异：**链表牺牲了 O(1) 访问，换取了 O(1) 的（特定位置）插入/删除**。
*   你了解了双向链表和循环链表的概念、优缺点及其适用场景。
*   你能够根据具体应用的需求，在数组和链表之间做出合理的技术选型。

我们已经学习了两种最基本的线性数据结构。接下来，我们将基于它们构建更高级的抽象数据类型。

如果你对链表的任何部分有疑问，请提出。确认无误后，我们将进入 **第四章：栈 (Stacks)**。