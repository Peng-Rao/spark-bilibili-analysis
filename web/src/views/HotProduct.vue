<template>
    <el-card class="introduce">

        <div id='zoom'></div>
    </el-card>
</template>

<script setup>
import { onMounted, onUnmounted, ref, reactive} from 'vue';
import axios from "axios";
let myChart = null;
onMounted(() => {
    if (window.echarts) {
        // 基于准备好的dom，初始化echarts实例
        myChart = window.echarts.init(document.getElementById('zoom'))

        // 指定图表的配置项和数据
        const option = {
            tooltip: {
                trigger: 'axis',
                show: false,
                axisPointer: { // 坐标轴指示器，坐标轴触发有效
                    type: 'shadow' // 默认为直线，可选为：'line' | 'shadow'
                },
            },
            grid: {
                left: '3%',
                right: '4%',
                bottom: '3%',
                containLabel: true
            },
            xAxis: [{
                splitLine: {
                    show: false
                },
                type: 'value',
                show: false,

            }],
        };
        axios.get("http://localhost:5000/SucceedGoodsTop").then(res =>
        {
            const data = res.data;
            const yAxis = {
                splitLine: {
                    show: false
                },
                axisLine: { //y轴
                    show: false
                },
                type: 'category',
                axisTick: {
                    show: false
                },
                data: data.map(item => item.item_name),
            }
            const series = [{
            name: '订单量',
            type: 'bar',
            barWidth: 30, // 柱子宽度
            label: {
                show: true,
                position: 'right', // 位置
                fontSize: 14,
                fontWeight: 'bold', // 加粗
                distance: 5 // 距离
            }, // 柱子上方的数值
            itemStyle: {
                barBorderRadius: [0, 20, 20, 0], // 圆角（左上、右上、右下、左下）
                color: new window.echarts.graphic.LinearGradient(0, 0, 1, 0, [
                    '#2FAEF2', '#1CD8A8'
                ].map((color, offset) => ({
                    color,
                    offset
                }))), // 渐变
            },
            data: data.map(item => item.count)
            }];
            option.yAxis = [yAxis];
            option.series = series;
            myChart.setOption(option)
        })
    }
})
onUnmounted(() => {
    myChart.dispose()
})
</script>

<style scoped>
#zoom {
    height: 400px;
}
.introduce .order {
    display: flex;
    margin-bottom: 50px;
}
.introduce .order .order-item {
    flex: 1;
    margin-right: 20px;
}
.introduce .order .order-item:last-child{
    margin-right: 0;
}
</style>