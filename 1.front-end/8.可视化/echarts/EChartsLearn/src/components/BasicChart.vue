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
    // 1. 标题
    title: {
      text: "2024年营收报表",
      subtext: "数据来源：财务部",
      left: "center", // 居中显示
    },

    // 2. 图例
    legend: {
      data: ["线上渠道", "线下门店"], // 必须对应 series 里的 name
      top: "bottom", // 放到底部
    },

    // 3. 绘图网格 (解决坐标轴文字显示不全问题)
    grid: {
      left: "20%",
      right: "4%",
      bottom: "10%", // 留出空间给图例
      containLabel: true, // 包含坐标轴文字
    },

    // 4. 工具箱
    toolbox: {
      feature: {
        saveAsImage: {}, // 提供下载图片按钮
      },
    },

    // 5. 提示框 (面试重点：Formatter)
    tooltip: {
      trigger: "axis", // 坐标轴触发
      axisPointer: { type: "shadow" }, // 鼠标放上去有阴影指示器
      // 自定义提示内容
      formatter: function (params) {
        // params 是一个数组，包含了当前轴上所有系列的数据
        let htmlStr = `<div style="font-weight:bold">${params[0].axisValue}</div>`;
        params.forEach((item) => {
          htmlStr += `
          <div style="margin-top:5px;">
            <span style="display:inline-block;width:10px;height:10px;border-radius:50%;background:${item.color};"></span>
            ${item.seriesName}: <b>${item.value}万</b>
          </div>
        `;
        });
        console.log(params);
        return htmlStr;
      },
    },

    // 6. X轴 (类目轴)
    xAxis: {
      type: "category",
      data: ["一月", "二月", "三月", "四月", "五月", "六月"],
      axisLabel: {
        rotate: 45, // 文字太长时倾斜显示
      },
    },

    // 7. Y轴 (数值轴)
    yAxis: {
      type: "value",
      name: "单位：万元", // 轴名称
      splitLine: {
        // 网格横线
        lineStyle: { type: "dashed" }, // 虚线
      },
    },

    // 8. 系列数据
    series: [
      {
        name: "线上渠道",
        type: "bar",
        data: [120, 200, 150, 80, 70, 110],
        itemStyle: {
          borderRadius: [5, 5, 0, 0], // 顶部圆角
        },
      },
      {
        name: "线下门店",
        type: "line", // 柱状图和折线图混合
        smooth: true, // 平滑曲线
        data: [80, 150, 100, 60, 50, 90],
        areaStyle: { opacity: 0.2 }, // 填充面积，变成面积图
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
