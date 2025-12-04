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
    //--------------- 组件层（5）----------------
    // 标题
    title: {
      text: "我的第一个 ECharts 图表",
      subtext: "BasicChart1.vue 示例",
      left: "center",
      textStyle: {
        color: "#2d7bed",
      },
      subtextStyle: {
        color: "#2dfbed",
      },
    },

    // 图例
    legend: {
      data: ["邮件营销", "联盟广告"],
      orient: "vertical",
    },

    // 绘图网格
    grid: {
      left: "100px",
      right: "100px",
      top: "100px",
      bottom: "100px",
      containLable: true,
    },

    // 提示框组件 (鼠标悬停时的浮层)
    tooltip: {
      trigger: "axis",
      // formatter:
    },

    // 工具箱
    toolbox: {
      feature: { saveAsImage: {} },
    },

    //--------------- 坐标系层（2）----------------
    // x 轴
    xAxis: {
      data: ["衬衫", "羊毛衫", "雪纺衫", "裤子", "高跟鞋", "袜子"],
    },

    // y 轴
    yAxis: {},

    //--------------- 数据层（1）----------------
    // 系列数据（核心数据区）
    series: [
      {
        name: "邮件营销",
        type: "line",
        stack: "总量", // 堆叠标记
        smooth: true, // 平滑
        areaStyle: {}, // 填充
        data: [120, 132, 101, 134, 90, 230, 210],
      },
      {
        name: "联盟广告",
        type: "line",
        stack: "总量", // 相同的标记，会堆叠在上一条线之上
        smooth: true,
        areaStyle: {},
        data: [220, 182, 191, 234, 290, 330, 310],
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
