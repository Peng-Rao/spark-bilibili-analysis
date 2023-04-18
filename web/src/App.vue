<template>
    <div class="app">
        <order-chart></order-chart>
        <success-goods-top></success-goods-top>
<!--        <ProvinceOrdersHeatmap></ProvinceOrdersHeatmap>-->
        <time-order></time-order>
    </div>
</template>

<script>
import OrderChart from './components/OrderPriceRange.vue'
import SuccessGoodsTop from './components/SucceedGoodsTop.vue'
// import ProvinceOrdersHeatmap from "@/components/ProvinceOrdersHeatmap.vue";
import TimeOrder from "@/components/TimeOrder.vue";

export default {
    name: 'App',
    components: {
        // ProvinceOrdersHeatmap,
        OrderChart,
        SuccessGoodsTop,
        TimeOrder
    }
}
</script>
<style>
</style>