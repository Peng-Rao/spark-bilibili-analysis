<template>
  <div id="ProvinceOrdersHeatmap" style="width:100%;height:500px;"></div>
</template>

<script>
import axios from 'axios';
import * as echarts from 'echarts';
export default {
    name: "ProvinceOrdersHeatmap",
    mounted() {
        axios.get('http://localhost:5000/ProvinceOrder')
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
            const chartDom = document.getElementById('ProvinceOrdersHeatmap');
            const chart = echarts.init(chartDom);
            const province = data.map(item => item.province_name);
            const t_data = data.map(item => item.count);
            const res = [];
            for (let j = 0; j < t_data.length; j++) {
                res.push({
                    name: province[j],
                    value: data[j]
                });
            }
            res.sort(function (a, b) {
                return a.value - b.value;
            });
            const res1 = [];
            const res2 = [];


            const option = {
                //提示框信息
                tooltip: {
                    show: true,
                    trigger: 'item',
                },
                //视觉映射
                visualMap: {
                    type: 'piecewise',
                    text: ['高', '低'],
                    pieces: [{
                        min: 301,
                        color: "#bb0000"
                    },
                        {
                            min: 201,
                            max: 300,
                            color: "#c92626"
                        },
                        {
                            min: 101,
                            max: 200,
                            color: "#ce6c2b"
                        },
                        {
                            min: 50,
                            max: 100,
                            color: "#c4aa29"
                        },
                        {
                            min: 11,
                            max: 50,
                            color: "#92b733"
                        },
                        {
                            min: 1,
                            max: 10,
                            color: "#6ead51"
                        },
                    ],
                    orient: 'vertical',
                    itemSymbol: 'circle',
                    itemWidth: 20,
                    itemHeight: 20,
                    showLabel: true,
                    seriesIndex: [0],
                    textStyle: {
                        color: '#7B93A7'
                    },
                    bottom: '2%',
                    left: "3%",
                },
                grid: {
                    right: '5%',
                    top: '20%',
                    bottom: '10%',
                    width: '20%'
                },
                xAxis: {
                    min: 0,
                    max: 4000,
                    show: false
                },
                yAxis: {
                    inverse: true,
                    offset: '2',
                    'type': 'category',
                    data: res1,
                    nameTextStyle: {
                        color: '#fff'
                    },
                    axisTick: {
                        show: false,
                    },
                    //Y轴刻度标签
                    axisLabel: {
                        textStyle: {
                            fontSize: 11,
                            color: '#000000',
                        },
                        interval: 0
                    },
                    axisLine: {
                        show: false
                    },
                    splitLine: {
                        show: false
                    },
                },
                // 地理坐标系组件,绘制地图,散点图等
                geo: {
                    map: "china",
                    roam: !1,
                    scaleLimit: {
                        min: 1,
                        max: 2
                    },
                    zoom: 1.2,
                    right: '35%',
                    left: '20%',
                    top: '20%',
                    label: {
                        normal: {
                            show: !0,
                            fontSize: "10",
                            color: "rgba(0,0,0,0.7)"
                        }
                    },
                    itemStyle: {
                        normal: {
                            //shadowBlur: 50,
                            //shadowColor: 'rgba(0, 0, 0, 0.2)',
                            borderColor: "rgba(0, 0, 0, 0.2)"
                        },
                        emphasis: {
                            areaColor: "#f2d5ad",
                            shadowOffsetX: 0,
                            shadowOffsetY: 0,
                            borderWidth: 0
                        }
                    }
                },
                //标题
                title: {
                    text: '全国各省订单分布情况',
                    //subtext: '   ',
                    left: '35%',
                    top: '1%',
                    textStyle: {
                        color: 'black',
                        fontSize: 25
                    }
                },
                series: [{
                    name: 'mapSer',
                    type: 'map',
                    map: 'china',
                    roam: false,
                    geoIndex: 0,
                    label: {
                        show: false,
                    },
                    data: res
                },
                    {
                        name: 'barSer',
                        type: 'bar',
                        zlevel: 3,
                        barWidth: '90%',
                        label: {
                            normal: {
                                show: true,
                                fontSize: 10,
                                position: 'right',
                                formatter: '{c}'
                            }
                        },
                        data: res2,
                        itemStyle: {
                            normal: {
                                color: function (params) {
                                    // build a color map as your need.
                                    var colorList = [{
                                        colorStops: [{
                                            offset: 0,
                                            color: '#FF0000' // 0% 处的颜色
                                        }, {
                                            offset: 1,
                                            color: '#990000' // 100% 处的颜色
                                        }]
                                    },
                                        {
                                            colorStops: [{
                                                offset: 0,
                                                color: '#00C0FA' // 0% 处的颜色
                                            }, {
                                                offset: 1,
                                                color: '#2F95FA' // 100% 处的颜色
                                            }]
                                        }
                                    ];
                                    if (params.dataIndex < 3) {
                                        return colorList[0]
                                    } else {
                                        return colorList[1]
                                    }
                                },
                            }
                        }
                    }
                ]
            };
            chart.setOption(option);
        }
    }
}
</script>

<style scoped>

</style>