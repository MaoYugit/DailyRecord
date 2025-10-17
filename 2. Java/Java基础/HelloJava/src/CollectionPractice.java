import java.util.ArrayList;
import java.util.List; // 引入 List 和 ArrayList
import java.util.HashSet;
import java.util.Set;
import java.util.HashMap;
import java.util.Map;

public class CollectionPractice {
    public static void main(String[] args) {
        // --- List 的使用 ---
        // 创建一个只能存放 String 类型元素的 ArrayList
        // List<String> 是父类引用，ArrayList<String> 是子类对象，这是多态的体现
        List<String> fruitList = new ArrayList<>();

        // 1. 添加元素 .add()
        fruitList.add("苹果");
        fruitList.add("香蕉");
        fruitList.add("橙子");
        System.out.println("水果列表: " + fruitList);

        // 2. 在指定位置添加元素
        fruitList.add(1, "葡萄"); // 在索引1的位置插入
        System.out.println("插入后: " + fruitList);

        // 3. 获取元素 .get(index)
        String secondFruit = fruitList.get(1);
        System.out.println("第二个水果是: " + secondFruit);

        // 4. 获取列表大小 .size()
        System.out.println("列表里有 " + fruitList.size() + " 种水果。");

        // 5. 修改元素 .set(index, newElement)
        fruitList.set(0, "红苹果");
        System.out.println("修改后: " + fruitList);

        // 6. 删除元素 .remove()
        fruitList.remove("香蕉"); // 按内容删除
        fruitList.remove(2);     // 按索引删除 (删除的是"橙子")
        System.out.println("删除后: " + fruitList);

        // 7. 遍历列表 (for-each 循环是最佳选择)
        System.out.println("--- 遍历水果列表 ---");
        for (String fruit : fruitList) {
            System.out.println(fruit);
        }

        System.out.println("\n--- Set 的使用 ---");
        Set<String> uniqueTags = new HashSet<>();

// 1. 添加元素
        uniqueTags.add("Java");
        uniqueTags.add("编程");
        uniqueTags.add("学习");
        System.out.println("标签集合: " + uniqueTags);

// 2. 尝试添加一个重复的元素
        boolean isAdded = uniqueTags.add("Java");
        System.out.println("再次添加'Java'是否成功? " + isAdded); // false
        System.out.println("添加重复元素后: " + uniqueTags); // 内容没有变化

// 3. 检查是否包含某个元素 .contains()
        System.out.println("是否包含'编程'标签? " + uniqueTags.contains("编程")); // true

// 4. 遍历 (只能用 for-each，因为没有索引)
        System.out.println("--- 遍历标签集合 ---");
        for (String tag : uniqueTags) {
            System.out.println(tag);
        }

        System.out.println("\n--- Map 的使用 ---");
// 创建一个 Map，Key是String类型(学生姓名)，Value是Integer类型(分数)
        Map<String, Integer> studentScores = new HashMap<>();

// 1. 添加键值对 .put(key, value)
        studentScores.put("张三", 95);
        studentScores.put("莉莉", 99);
        studentScores.put("王五", 82);
        System.out.println("学生分数表: " + studentScores);

// 2. 如果 key 已存在，put 会覆盖旧的 value
        studentScores.put("张三", 98); // 张三的分数被更新了
        System.out.println("更新后: " + studentScores);

// 3. 通过 key 获取 value .get(key)
        int liliScore = studentScores.get("莉莉");
        System.out.println("莉莉的分数是: " + liliScore);

// 4. 检查是否包含某个 key .containsKey(key)
        System.out.println("是否包含'赵六'的成绩? " + studentScores.containsKey("赵六")); // false

// 5. 遍历 Map (最常用的方式是遍历 key)
        System.out.println("--- 遍历分数表 ---");
        for (String name : studentScores.keySet()) { // .keySet() 返回所有 key 的集合
            int score = studentScores.get(name);
            System.out.println(name + " 的分数是: " + score);
        }

// 另一种高效的遍历方式 (entrySet)
        System.out.println("--- 使用 entrySet 遍历 ---");
        for (Map.Entry<String, Integer> entry : studentScores.entrySet()) {
            System.out.println(entry.getKey() + " => " + entry.getValue());
        }
    }
}