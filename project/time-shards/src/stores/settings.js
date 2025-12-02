import { defineStore } from 'pinia';
import { ref, watch } from 'vue';

export const useSettingsStore = defineStore('settings', () => {
    // 鼠标特效设置
    const mouseEffect = ref(localStorage.getItem('mouseEffect') || 'flower');
    const mouseEffectEnabled = ref(localStorage.getItem('mouseEffectEnabled') !== 'false');

    // 背景设置
    const backgroundType = ref(localStorage.getItem('backgroundType') || 'shards');

    // 监听变化并持久化
    watch(mouseEffect, (val) => localStorage.setItem('mouseEffect', val));
    watch(mouseEffectEnabled, (val) => localStorage.setItem('mouseEffectEnabled', val));
    watch(backgroundType, (val) => localStorage.setItem('backgroundType', val));

    // 设置选项定义（用于UI渲染）
    const mouseEffectOptions = [
        { type: 'flower', name: '花朵', icon: '🌸', description: '鼠标移动时绽放花朵' },
        { type: 'firework', name: '烟花', icon: '🎆', description: '点击时爆发烟花' },
        { type: 'star', name: '星光', icon: '✨', description: '鼠标留下星光轨迹' }
    ];

    const backgroundOptions = [
        { type: 'shards', name: 'Time Shards', icon: '💎', description: '时间碎片 - 科技青色' },
        { type: 'nebula', name: 'Cosmic Nebula', icon: '🌌', description: '宇宙星云 - 紫粉渐变' },
        { type: 'matrix', name: 'Matrix Rain', icon: '💚', description: '矩阵代码雨 - 赛博朋克' },
        { type: 'galaxy', name: 'Galaxy Spiral', icon: '🌀', description: '星系螺旋 - 深蓝金色' }
    ];

    // Actions
    const setMouseEffect = (type) => {
        mouseEffect.value = type;
    };

    const toggleMouseEffect = (enabled) => {
        mouseEffectEnabled.value = enabled;
    };

    const setBackgroundType = (type) => {
        backgroundType.value = type;
    };

    return {
        mouseEffect,
        mouseEffectEnabled,
        backgroundType,
        mouseEffectOptions,
        backgroundOptions,
        setMouseEffect,
        toggleMouseEffect,
        setBackgroundType
    };
});
