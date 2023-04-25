<template>
        <div class="container">
            <iframe :src="webUrl" height="100%" width="100%"></iframe>
        </div>
</template>

<script setup>
const webUrl = 'https://www.baidu.com'
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