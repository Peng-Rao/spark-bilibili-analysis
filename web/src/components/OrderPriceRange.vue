<template>
    <div id="OrderPriceRange" style="width:100%;height:300px;"></div>
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
                title: {
                    text: 'Price Distribution'
                },
                tooltip: {
                    trigger: 'item',
                    formatter: '{b} : {c} ({d}%)'
                },
                legend: {
                    orient: 'horizontal',
                    bottom: 10,
                    data: this.data.map(item => item.price_range)
                },
                series: [
                    {
                        type: 'pie',
                        radius: '50%',
                        center: ['50%', '50%'],
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
