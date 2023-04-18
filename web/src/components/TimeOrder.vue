<template>
<div id="TimeOrder" style="width:100%;height:500px;"></div>
</template>

<script>
import axios from 'axios';
import * as echarts from 'echarts';
export default {
    name: "TimeOrder",
    mounted() {
        axios.get('http://localhost:5000/TimeOrder')
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
            const chartDom = document.getElementById('TimeOrder');
            const chart = echarts.init(chartDom);
            const time_point_list = data.map(item => item.time_point);
            const count_list = data.map(item => item.count);
            const option = {
                backgroundColor:"#101e44",
                grid: {
                    top: 100,
                    containLabel: true
                },
                tooltip: {
                    trigger: 'axis',
                    formatter: '{b0}: {c0}'
                },
                xAxis: [{
                    type: "category",
                    boundaryGap: false,
                    axisLabel: {
                        formatter: '{value}',
                        fontSize:14,
                        margin:20,
                        textStyle: {
                            color: "#7ec7ff"
                        }
                    },
                    axisLine: {
                        show:false,
                    },
                    splitLine: {
                        show:false,
                        lineStyle: {
                            color: "#243753"
                        }
                    },
                    axisTick:{
                        show:false
                    },
                    data: time_point_list
                }],
                yAxis: [
                    {
                        boundaryGap:false,
                        type: "value",
                        axisLabel: {
                            textStyle: {
                                color: "#7ec7ff"
                            }
                        },
                        nameTextStyle: {
                            color: "#fff",
                            fontSize: 12,
                            lineHeight: 40
                        },
                        splitLine: {
                            lineStyle: {
                                color: "#243753",
                                type:'dashed',
                            }
                        },
                        axisLine: {
                            show: false,
                            lineStyle:{
                                color:"#283352"
                            }
                        },
                        axisTick: {
                            show: false
                        }
                    }
                ],
                series: [{
                    name: "报名",
                    type: "line",
                    smooth: true,
                    showSymbol: true,
                    symbolSize: 10,
                    zlevel: 3,
                    itemStyle:{
                        color:'#19a3df',
                        borderColor:"#a3c8d8"
                    },
                    lineStyle: {
                        normal: {
                            width:6,
                            color: "#19a3df",
                        }
                    },
                    areaStyle: {
                        normal: {
                            color: new echarts.graphic.LinearGradient(
                                0,
                                0,
                                0,
                                1,
                                [{
                                    offset: 0,
                                    color: "rgba(88,255,255,.6)"
                                },
                                    {
                                        offset: 1,
                                        color: "rgba(88,255,255,0)"
                                    }
                                ],
                                false
                            ),
                        }
                    },
                    data: count_list
                }]
            };
            chart.setOption(option);
        }
    }
}
</script>

<style scoped>

</style>