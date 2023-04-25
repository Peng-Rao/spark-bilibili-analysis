import { createApp } from 'vue'
import router from '@/router'
import { ElButton } from 'element-plus'
import App from './App.vue'
import * as ElementPlusIconsVue from '@element-plus/icons-vue'

const app = createApp(App)
for (const [key, component] of Object.entries(ElementPlusIconsVue)) {
    app.component(key, component)
}

app.use(ElButton)
app.use(router) // 引用路由实例
app.mount('#app')