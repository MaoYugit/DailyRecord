<template>
  <div ref="chartRef" class="chart-container"></div>
</template>

<script setup>
import { ref, onMounted, onUnmounted } from "vue";
import * as echarts from "echarts";

const chartRef = ref(null);
let mychart = null;

const initChart = () => {
  mychart = echarts.init(chartRef.value);
  const option = {
    // 1. 标题
    title: {
      text: "my first echarts",
      subtext: "sub title",
      left: "center",
      textStyle: {
        color: "#333",
        fontSize: 24,
        fontWeight: "bold",
        fontFamily: "Arial",
      },
      subtextStyle: {
        color: "#333",
        fontSize: 14,
      },
    },
    // 2. 图例
    legend: {
      data: ["一组", "二组", "三组", "四组", "五组"],
      orient: "vertical", // horizontal
      right: "10px",
      top: "center",
      icon: "circle",
      type: "scroll",
      selected: {
        一组: true,
        二组: false,
        三组: false,
        四组: false,
        五组: false,
      },
    },
    // 3. Grid 布局
    grid: {
      right: 150,
      left: 50,
      top: 60,
      bottom: 40,
      containLabel: true,
      show: true,
      backgroundColor: "#f4f4f4",
      borderColor: "#000",
      borderWidth: 1,
    },
    // 4. 提示框
    tooltip: {
      trigger: "axis",
      type: "shadow",
    },
    // 5. 工具箱
    toolbox: {
      saveAsImage: { show: true },
      dataView: { show: true },
      magicType: { type: ["line", "bar", "stack"] },
    },
    // 6. x 轴
    xAxis: {
      type: "category",
      data: ["周一", "周二", "周三", "周四", "周五"],
    },
    // 7. y 轴
    yAxis: {
      type: "value",
    },
    // 8. 数据
    series: [
      {
        name: "一组",
        type: "bar",
        data: [20, 49, 70, 23, 56],
        barWidth: 20, // 柱子别太粗，精致点

        // 样式：圆角 + 渐变色
        itemStyle: {
          borderRadius: [10, 10, 0, 0], // 上方圆角
          color: {
            type: "linear",
            x: 0,
            y: 0,
            x2: 0,
            y2: 1, // 从上到下渐变
            colorStops: [
              { offset: 0, color: "#83bff6" }, // 0% 处的颜色
              { offset: 1, color: "#188df0" }, // 100% 处的颜色
            ],
          },
        },

        // 标签：直接显示数值在柱子顶部
        label: {
          show: true,
          position: "top",
          color: "#188df0",
        },
      },
      {
        name: "二组",
        type: "bar",
        data: [35, 45, 55, 65, 75],
        barWidth: 20, // 柱子别太粗，精致点

        // 样式：圆角 + 渐变色
        itemStyle: {
          borderRadius: [10, 10, 0, 0], // 上方圆角
          color: {
            type: "linear",
            x: 0,
            y: 0,
            x2: 0,
            y2: 1, // 从上到下渐变
            colorStops: [
              { offset: 0, color: "#83bff6" }, // 0% 处的颜色
              { offset: 1, color: "#188df0" }, // 100% 处的颜色
            ],
          },
        },

        // 标签：直接显示数值在柱子顶部
        label: {
          show: true,
          position: "top",
          color: "#188df0",
        },
      },
      {
        name: "三组",
        type: "bar",
        data: [80, 20, 95, 30, 60],
        barWidth: 20, // 柱子别太粗，精致点

        // 样式：圆角 + 渐变色
        itemStyle: {
          borderRadius: [10, 10, 0, 0], // 上方圆角
          color: {
            type: "linear",
            x: 0,
            y: 0,
            x2: 0,
            y2: 1, // 从上到下渐变
            colorStops: [
              { offset: 0, color: "#83bff6" }, // 0% 处的颜色
              { offset: 1, color: "#188df0" }, // 100% 处的颜色
            ],
          },
        },

        // 标签：直接显示数值在柱子顶部
        label: {
          show: true,
          position: "top",
          color: "#188df0",
        },
      },
      {
        name: "四组",
        type: "bar",
        data: [15, 10, 18, 12, 22],
        barWidth: 20, // 柱子别太粗，精致点

        // 样式：圆角 + 渐变色
        itemStyle: {
          borderRadius: [10, 10, 0, 0], // 上方圆角
          color: {
            type: "linear",
            x: 0,
            y: 0,
            x2: 0,
            y2: 1, // 从上到下渐变
            colorStops: [
              { offset: 0, color: "#83bff6" }, // 0% 处的颜色
              { offset: 1, color: "#188df0" }, // 100% 处的颜色
            ],
          },
        },

        // 标签：直接显示数值在柱子顶部
        label: {
          show: true,
          position: "top",
          color: "#188df0",
        },
      },
      {
        name: "五组",
        type: "bar",
        data: [30, 60, 90, 60, 30],
        barWidth: 20, // 柱子别太粗，精致点

        // 样式：圆角 + 渐变色
        itemStyle: {
          borderRadius: [10, 10, 0, 0], // 上方圆角
          color: {
            type: "linear",
            x: 0,
            y: 0,
            x2: 0,
            y2: 1, // 从上到下渐变
            colorStops: [
              { offset: 0, color: "#83bff6" }, // 0% 处的颜色
              { offset: 1, color: "#188df0" }, // 100% 处的颜色
            ],
          },
        },

        // 标签：直接显示数值在柱子顶部
        label: {
          show: true,
          position: "top",
          color: "#188df0",
        },
      },
    ],
  };
  mychart.setOption(option);
};

onMounted(() => {
  initChart();
});

onUnmounted(() => {
  if (mychart != null) {
    mychart.dispose();
    mychart = null;
  }
});
</script>

<style scoped>
.chart-container {
  width: 100%;
  height: 600px;
}
</style>
