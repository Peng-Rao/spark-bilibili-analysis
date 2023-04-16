<template>
    <div id="SucceedGoodsTop" style="height: 300px;"></div>
</template>

<script>
import axios from 'axios';
import echarts from 'echarts';

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
                title: {
                    text: 'Item Count'
                },
                tooltip: {},
                xAxis: {
                    type: 'value',
                    name: 'Count'
                },
                yAxis: {
                    type: 'category',
                    data: data.map(item => item.item_name),
                    name: 'Item Name'
                },
                series: [{
                    name: 'Count',
                    type: 'bar',
                    data: data.map(item => item.count),
                    label: {
                        show: true,
                        position: 'left'
                    }
                }]
            };

            chart.setOption(option);
        }
    }
}
</script>
