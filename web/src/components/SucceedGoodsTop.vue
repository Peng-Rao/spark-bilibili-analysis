<template>
    <div id="SucceedGoodsTop" style="width:100%;height:500px;"></div>
</template>

<script>
import axios from 'axios';
import * as echarts from 'echarts';

export default {
    name: 'SucceedGoodsTop',
    mounted() {
        axios.get('http://localhost:5000/SucceedGoodsTop')
            .then(response => {
                const data = response.data;
                this.renderChart(data)
            })
            .catch(error => {
                console.log(error);
            });
    },
    methods: {
        renderChart(data) {
            const chartDom = document.getElementById('SucceedGoodsTop');
            const chart = echarts.init(chartDom);

            const option = {
                backgroundColor: '#04243E',
                tooltip: {
                    trigger: 'axis',
                    show: false,
                    axisPointer: { // 坐标轴指示器，坐标轴触发有效
                        type: 'shadow' // 默认为直线，可选为：'line' | 'shadow'
                    },
                },
                legend: {
                    selectedMode: false,
                    textStyle: {
                        color:"#fff"
                    }
                },
                grid: {
                    left: '3%',
                    right: '4%',
                    bottom: '3%',
                    containLabel: true
                },
                title: {
                    text: 'Item Count'
                },
                xAxis: [{
                    splitLine: {
                        show: false
                    },
                    type: 'value',
                    show: false,

                }],
                yAxis: [{
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
                    axisLabel: {
                        textStyle: {
                            color:"#fff"
                        }
                    }
                }],
                series: [
                    {
                        name: '订单量',
                        type: 'bar',
                        barWidth: 30, // 柱子宽度
                        label: {
                            show: true,
                            position: 'right', // 位置
                            color: '#1CD8A8',
                            fontSize: 14,
                            fontWeight: 'bold', // 加粗
                            distance: 5 // 距离
                        }, // 柱子上方的数值
                        itemStyle: {
                            barBorderRadius: [0, 20, 20, 0], // 圆角（左上、右上、右下、左下）
                            color: new echarts.graphic.LinearGradient(0, 0, 1, 0, [
                                '#2FAEF2', '#1CD8A8'
                            ].map((color, offset) => ({
                                color,
                                offset
                            }))), // 渐变
                        },
                        data: data.map(item => item.count)
                    }
                ]
            };

            chart.setOption(option);
        }
    }
}
</script>