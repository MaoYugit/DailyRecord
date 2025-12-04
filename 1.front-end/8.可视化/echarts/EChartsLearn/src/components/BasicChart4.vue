<template>
  <!-- 1. DOM 容器准备：必须要有宽度和高度 -->
  <div ref="chartRef" class="chart-container"></div>
</template>

<script setup>
import { ref, onMounted, onUnmounted } from "vue";
// 引入 echarts 核心模块
import * as echarts from "echarts";

// 定义 DOM 引用
const chartRef = ref(null);
let myChart = null; // 用于保存 chart 实例

onMounted(() => {
  // 核心步骤都在这里执行
  initChart();
});

onUnmounted(() => {
  // 销毁实例，防止内存泄漏（企业级开发好习惯）
  if (myChart != null) {
    // 告诉 ECharts 内部：“把你的定时器关了，把监听器撤了，把 Canvas 删了”
    myChart.dispose();
    // 将变量置空，断开 JavaScript 变量引用
    // 帮助 JS 垃圾回收机制（GC）识别这块内存可以被回收了
    myChart = null;
  }
});

const initChart = () => {
  // 2. echarts.init：初始化实例
  // 参数是 DOM 节点，Vue 中通过 ref.value 获取
  myChart = echarts.init(chartRef.value);

  // 3. 配置 option：定义图表的数据和样式（这是未来打交道最多的地方）
  const option = {
    tooltip: {
      trigger: "axis",
      axisPointer: { type: "cross" }, // 十字准星指示器，更专业
    },
    legend: {
      data: ["销售额", "毛利率"],
    },
    xAxis: [
      {
        type: "category",
        data: ["1月", "2月", "3月", "4月", "5月", "6月"],
        axisPointer: { type: "shadow" },
      },
    ],
    // 重点：Y轴变成了一个数组
    yAxis: [
      {
        type: "value",
        name: "销售额",
        min: 0,
        max: 2500,
        interval: 500,
        axisLabel: { formatter: "{value} 元" },
      },
      {
        type: "value",
        name: "毛利率",
        min: 0,
        max: 100, // 百分比通常 0-100
        interval: 20,
        axisLabel: { formatter: "{value} %" },
        // 这一行决定了它在右边
        position: "right",
        // 去掉右侧的网格线，防止和左侧的网格线重叠显得乱
        splitLine: { show: false },
      },
    ],
    series: [
      {
        name: "销售额",
        type: "bar",
        // 默认 yAxisIndex: 0，走左轴
        data: [200, 490, 700, 232, 256, 767],
        itemStyle: { color: "#5470C6" },
      },
      {
        name: "毛利率",
        type: "line",
        // ⚠️ 关键点：指定走索引为 1 的轴（即右轴）
        yAxisIndex: 1,
        data: [20, 22, 33, 45, 63, 12],
        itemStyle: { color: "#91CC75" },
      },
    ],
  };

  // 4. setOption：将配置注入实例
  // 5. 渲染：ECharts 会自动根据 option 画出图表
  myChart.setOption(option);
};
</script>

<style scoped>
.chart-container {
  width: 100%;
  height: 400px; /* ⚠️ 重点：如果没有高度，图表将无法显示 */
  background-color: #f0f2f5;
}
</style>
