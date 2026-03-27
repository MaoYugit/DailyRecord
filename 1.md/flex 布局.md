# flex 布局

1. flex-direction

   - row（默认），水平方向

   - column，垂直方向
   - row-reverse，水平方向，从右到左
   - column-reverse，垂直方向，从下到上

2. justify-content（主轴对齐方式）

   - flex-start（默认），紧贴主轴开头排列，顺序不变
   - flex-end，紧贴主轴末尾排列，顺序不变
   - center，所有元素在主轴方向居中，顺序不变
   - space-between，首尾元素贴边，元素均匀排列，顺序不变
   - spce-around，首尾元素不贴边，元素均匀排列，首尾元素与左右边界的距离是元素间的距离的二分之一，，顺序不变
   - spece-evenly，元素均匀排列，首尾元素与边界的距离和元素之间的距离相等，，顺序不变
   - start，等同于flex-start
   - end，等同于flex-end

3. align-items（每一行对于交叉行的行为）

   - flex-start，交叉轴起点
   - flex-end，交叉轴终点

   - center，交叉轴居中

   - start，等同于flex-start

   - end，等同于flex-end

4. gap

   边界设置元素之间的间距

5. flex-wrap

   - wrap，一行装不下就换行，换行之后如果不使用align-content,那么每一行都会有自己的主轴和交叉轴，justify-content和align-items控制每一行各自对其方式
   - nowrap，不换行

6. align-content（所有行对于交叉轴的行为）

   - stretch（默认），当设置wrap的时候，换的行会把每一行平均分配在整个交叉轴方向，行与行之间的间距看起来会比较大
   - space-between，首尾贴边，其他均分
   - space-evenly，所有均分
   - flex-start，排在交叉轴开始
   - flex-end，排在交叉轴末尾
   - center，居中

7. flex-shink，动态缩小，设置在每一个元素上

   - 1，开启
   - 0，关闭
   - 不同数值，按照比例来确定缩小的速度，数字越大缩小越快
   - 常与 min-width 一起用，表示最小能缩多小
   - 与媒体查询一起用，当页面小于一个特定值，给父容器增加 flex-wrap: wrap; 换行，防止溢出

8. flex-grow，动态放大，设置在每一个元素上

   - 1，开启
   - 0，关闭
   - 不同数值，按照比例来确定放大的速度，数字越大放大越快
   - 常与 max-width 一起用，表示最大能放大多少

9. align-self，对于单个元素在交叉轴上的位置

   - flex-end，起始
   - flex-start，末尾
   - center，居中

10. 没有 justify-self。如果希望一个在最左侧，其余在最右侧，可以用 margin-right: auto;