<template>
    <div id="ProvinceOrder" style="height: 800px"></div>
</template>

<script>
import axios from 'axios'
import echarts from 'echarts'

export default {
    name: 'ProvinceOrder',
    data() {
        return {
            heatmapChart: null
        }
    },
    mounted() {
        this.fetchData()
    },
    methods: {
        fetchData() {
            axios
                .get('http://localhost:5000/ProvinceOrder')
                .then(response => {
                    const data = response.data
                    // 使用 ECharts 绘制热力图
                    this.drawHeatmap(data)
                })
                .catch(error => {
                    console.log(error)
                })
        },
        drawHeatmap(data) {
            this.heatmapChart = echarts.init(document.getElementById('ProvinceOrder'))
            const option = {
                tooltip: {
                    trigger: 'item'
                },
                visualMap: {
                    type: 'continuous',
                    min: 0,
                    max: 100,
                    text: ['High', 'Low'],
                    realtime: false,
                    calculable: true,
                    color: ['#d94e5d','#eac736','#50a3ba']
                },
                series: [{
                    name: '中国',
                    type: 'map',
                    mapType: 'china',
                    roam: false,
                    label: {
                        normal: {
                            show: true
                        },
                        emphasis: {
                            show: true
                        }
                    },
                    data: data
                }]
            }
            this.heatmapChart.setOption(option)
        }
    }
}
</script>