import { createRouter, createWebHashHistory } from 'vue-router';
import Index from '@/views/Index.vue';
import AddGood from '@/views/AddGood.vue';
import PriceRange from '@/views/PriceRange.vue';
import HotProduct from '@/views/HotProduct.vue';
import ProvinceOrder from "@/views/ProvinceOrder.vue";
import OrderDetail from "@/views/OrderDetail.vue";
import NameNode from "../views/NameNode.vue";

const router = createRouter({
    history: createWebHashHistory(), // hash 模式
    routes: [
        {
            path: '/',
            name: 'index',
            component: Index
        },
        {
            path: '/add',
            name: 'add',
            component: AddGood
        },
        {
            path: '/price-range',
            name: 'price-range',
            component: PriceRange
        },
        {
            path: '/hot-product',
            name: 'hot-product',
            component: HotProduct
        },
        {
            name: 'province-order',
            path: '/province-order',
            component: ProvinceOrder
        },
        {
            name: 'order-detail',
            path: '/order-detail',
            component: OrderDetail
        },
        {
            name: 'name-node',
            path: '/name-node',
            component: NameNode
        }
    ]
})

export default router