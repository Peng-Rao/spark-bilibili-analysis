<template>
    <div id="OrderPriceRange" style="width:100%;height:500px;"></div>
</template>
<script>
import axios from 'axios';
import * as echarts from 'echarts';

export default {
    name: 'OrderPriceRange',
    data () {
        return {
            data: []
        }
    },
    mounted () {
        axios.get('http://localhost:5000/OrderPriceRange')
            .then(response => {
                this.data = response.data;
                this.renderChart();
            })
            .catch(error => {
                console.log(error);
            });
    },
    methods: {
        renderChart() {
            const chartDom = document.getElementById('OrderPriceRange');
            const chart = echarts.init(chartDom);
            const option = {
                backgroundColor: '#04243E',
                tooltip: {
                    trigger: 'item',
                },
                legend: {
                    orient: 'horizontal',
                    bottom:"15%",
                    left: "20%",
                    textStyle: {
                        color:"#fff"
                    },
                },
                toolbox: {
                    show: false
                },
                series: [
                    {
                        name: '',
                        type: 'pie',
                        clockWise: false,
                        radius: [100, 200],
                        hoverAnimation: false,
                        data: this.data.map(item => ({
                            name: item.price_range,
                            value: item.count
                        })),
                        itemStyle: {
                            emphasis: {
                                shadowBlur: 10,
                                shadowOffsetX: 0,
                                shadowColor: 'rgba(0, 0, 0, 0.5)'
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