<template>
    <el-card class="introduce">
        <div class="order">
            <el-card class="order-item">
                <template #header>
                    <div class="card-header">
                        <span>最大订单</span>
                    </div>
                </template>
                <div class="item">{{ maxValue }}</div>
            </el-card>
            <el-card class="order-item">
                <template #header>
                    <div class="card-header">
                        <span>最小订单</span>
                    </div>
                </template>
                <div class="item">{{ minValue }}</div>
            </el-card>
        </div>
        <div id='zoom'></div>
    </el-card>
</template>

<script setup>
import { onMounted, onUnmounted, ref, reactive} from 'vue';
import ChinaMapJson from '@/assets/geo/china.json';
import axios from "axios";
let myChart = null;
let maxValue = ref(0);
let minValue = ref(0);

onMounted(() => {
    if (window.echarts) {
        // 基于准备好的dom，初始化echarts实例
        myChart = window.echarts.init(document.getElementById('zoom'))
        window.echarts.registerMap('china', ChinaMapJson);
        // 指定图表的配置项和数据
        const option = {
            title: {
                text: '各省订单分布',
            },
            tooltip: {
                trigger: 'item',
                formatter: function(params) {
                    return `${params.name}: ${params.value || 0}`

                }
            },
            toolbox: {
                show: true,
                orient: 'vertical',
                left: 'right',
                top: 'center',
                feature: {
                    dataView: { readOnly: false },
                    restore: {},
                    saveAsImage: {}
                }
            },
            visualMap: {
                min: 100,
                max: 500,
                text: ['High', 'Low'],
                realtime: false,
                calculable: true,
                inRange: {
                    color: ['lightskyblue', 'yellow', 'orangered']
                }
            }
        };
        axios.get('http://localhost:5000/ProvinceOrder').then(res => {
            const data = res.data;
            const series = {
                type: 'map',
                map: 'china',
                label: {
                    show: true
                },
                itemStyle: {
                    emphasis: {
                        borderColor: '#000',
                        borderWidth: 0.5,
                        areaColor: '#ccc',
                    }
                },
                data: data.map(item => {
                    return {
                        name: item.province_name,
                        value: item.count
                    }
                })
            }
            option.series = [series];
            myChart.setOption(option);
            const counts = data.map(item => item.count);
            minValue.value = Math.min(...counts);
            maxValue.value = Math.max(...counts);
        })
        // 使用刚指定的配置项和数据显示图表。
        setInterval(() => {
            axios.get('http://localhost:5000/ProvinceOrder').then(res => {
                const data = res.data;
                console.log(data.map(item => {
                    return {
                        name: item.province_name,
                        value: item.count
                    }
                }))
                const series = {
                    name: '香港18区人口密度',
                    type: 'map',
                    map: 'china',
                    label: {
                        show: true
                    },
                    data: data.map(item => {
                        return {
                            name: item.province_name,
                            value: item.count
                        }
                    })
                }
                option.series = [series];
                myChart.setOption(option);
                const counts = data.map(item => item.count);
                console.log(counts)
                minValue.value = Math.min(...counts);
                maxValue.value = Math.max(...counts);
            })
        }, 10000);
    }
})
onUnmounted(() => {
    myChart.dispose()
})
</script>

<style scoped>
#zoom {
    height: 500px;
}
</style>