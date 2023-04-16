<template>
    <div class="app">
        <div>
            <order-chart></order-chart>
        </div>
        <div>
            <succeed-goods-top></succeed-goods-top>
        </div>
        <div>
            <province-order></province-order>
        </div>
    </div>
</template>

<script>
import OrderChart from './components/OrderPriceRange.vue'
import SucceedGoodsTop from './components/SucceedGoodsTop.vue'
import ProvinceOrder from "@/components/ProvinceOrder.vue";

export default {
    name: 'App',
    components: {
        OrderChart,
        SucceedGoodsTop,
        ProvinceOrder
    }
}
</script>
<style>
</style>
