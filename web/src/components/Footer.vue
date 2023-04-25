<template>
    <div className="footer">
        <div className="left">Copyright © 2023 raopend. All rights reserved.</div>
        <div className="right">
            <a target="_blank" href="https://github.com/newbee-ltd/vue3-admin">spark-bilibili-analysis</a>
        </div>
    </div>
</template>

<script>
export default {
    name: 'Footer'
}
</script>

<style scoped>
.footer {
    height: 50px;
    border-top: 1px solid #e9e9e9;
    display: flex;
    justify-content: space-between;
    align-items: center;
    padding: 0 20px;
}
</style>