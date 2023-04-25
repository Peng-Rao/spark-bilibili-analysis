<template>
    <el-card class="introduce">
        <div class="order">
            <el-card class="order-item">
                <template #header>
                    <div class="card-header">
                        <span>今日订单数</span>
                    </div>
                </template>
                <div class="item">1888</div>
            </el-card>
            <el-card class="order-item">
                <template #header>
                    <div class="card-header">
                        <span>今日日活</span>
                    </div>
                </template>
                <div class="item">36271</div>
            </el-card>
            <el-card class="order-item">
                <template #header>
                    <div class="card-header">
                        <span>转化率</span>
                    </div>
                </template>
                <div class="item">20%</div>
            </el-card>
        </div>
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
        updateChart();
        setInterval(() => {
            updateChart();
        }, 5000);
    }
    function updateChart() {
        axios.get('http://localhost:5000/OrderPriceRange').then(res => {
            const option = {
                title: {
                    text: '价格分布区间',
                    left: 'center'
                },
                tooltip: {
                    trigger: 'item'
                },
                legend: {
                    orient: 'vertical',
                    left: 'left'
                },
                series: [
                    {
                        name: 'Access From',
                        type: 'pie',
                        radius: '50%',
                        data: res.data.map(item => {
                            return {
                                name: item.price_range,
                                value: item.count
                            }
                        }),
                        emphasis: {
                            itemStyle: {
                                shadowBlur: 10,
                                shadowOffsetX: 0,
                                shadowColor: 'rgba(0, 0, 0, 0.5)'
                            }
                        }
                    }
                ]
            };
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