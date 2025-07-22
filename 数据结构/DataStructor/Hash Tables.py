class HashTable:
    """
    使用链地址法实现的哈希表。
    """
    def __init__(self, size=16):
        if size <= 0:
            raise ValueError("Size must be positive")
        self._size = size
        self._buckets = [[] for _ in range(self._size)] # 初始化每个桶为一个空列表（模拟链表）

    def _hash(self, key):
        """
        一个简单的哈希函数。
        注意：对于字符串，Python内置的hash()是为每次运行随机化的，不适用于此。
        我们自己实现一个简单的、确定性的哈希函数。
        """
        hash_code = 0
        for char in str(key):
            hash_code = (hash_code * 31 + ord(char)) & 0xFFFFFFFF # 31是常见的乘数
        return hash_code

    def _get_bucket_index(self, key):
        """
        获取键对应的桶索引。
        """
        hash_code = self._hash(key)
        return hash_code % self._size

    def set(self, key, value):
        """
        设置键值对（插入或更新）。
        时间复杂度: 平均 O(1)，最坏 O(n)。
        """
        index = self._get_bucket_index(key)
        bucket = self._buckets[index]

        # 遍历桶（链表）查找键是否存在
        for i, (k, v) in enumerate(bucket):
            if k == key:
                # 键已存在，更新值
                bucket[i] = (key, value)
                return

        # 键不存在，在链表末尾添加新节点
        bucket.append((key, value))

    def get(self, key):
        """
        根据键获取值。
        时间复杂度: 平均 O(1)，最坏 O(n)。
        """
        index = self._get_bucket_index(key)
        bucket = self._buckets[index]

        for k, v in bucket:
            if k == key:
                return v

        # 键不存在
        raise KeyError(f"Key '{key}' not found")

    def delete(self, key):
        """
        根据键删除键值对。
        时间复杂度: 平均 O(1)，最坏 O(n)。
        """
        index = self._get_bucket_index(key)
        bucket = self._buckets[index]

        for i, (k, v) in enumerate(bucket):
            if k == key:
                # 找到并删除
                del bucket[i]
                return

        # 键不存在
        raise KeyError(f"Key '{key}' not found")

# --- 使用示例 ---
ht = HashTable(size=4)
ht.set("name", "Alice")
ht.set("age", 30)
ht.set("city", "New York") # 假设 'age' 和 'city' 发生冲突
ht.set("country", "USA")

print(f"Name: {ht.get('name')}")   # Alice
print(f"Age: {ht.get('age')}")     # 30

ht.set("age", 31) # 更新
print(f"New Age: {ht.get('age')}") # 31

ht.delete("city")
try:
    ht.get("city")
except KeyError as e:
    print(e) # Key 'city' not found