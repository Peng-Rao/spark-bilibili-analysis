<template>
    <el-table
            :data="tableData"
            border
            max-height="650"
            style="width: 100%">
        <el-table-column
                prop="order_id"
                label="订单编号"
                width="180">
        </el-table-column>
        <el-table-column
                prop="sku_name"
                label="名称"
                width="180">
        </el-table-column>
        <el-table-column
                prop="order_price"
                label="购买价格">
        </el-table-column>
        <el-table-column
                prop="sku_num"
                label="购买数量">
        </el-table-column>
        <el-table-column
                prop="create_time"
                label="创建时间">
        </el-table-column>
    </el-table>
</template>

<script setup>
import { ref } from 'vue';
import axios from "axios";
const tableData = ref([]);
setInterval(() => {
    axios.get('http://127.0.0.1:5000/OrderDetail').then(response => {
        tableData.value = response.data;
    });
}, 5000);

axios.get('http://127.0.0.1:5000/OrderDetail').then(response => {
    tableData.value = response.data;
});
</script>
