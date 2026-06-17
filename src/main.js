import Vue from 'vue'
import App from './App.vue'
import router from './router'
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import axios from 'axios'

Vue.config.productionTip = false

// 配置后端地址（根据你的后端端口修改）
axios.defaults.baseURL = 'http://localhost:8081/dms'
Vue.prototype.$http = axios

// 请求拦截器：自动携带 token
axios.interceptors.request.use(
  config => {
    const token = localStorage.getItem('token')
    if (token) {
      config.headers['Authorization'] = token
    }
    return config
  },
  error => Promise.reject(error)
)

// 响应拦截器：401 则跳转登录
axios.interceptors.response.use(
  response => response,
  error => {
    if (error.response && error.response.status === 401) {
      localStorage.removeItem('token')
      router.push('/login')
    }
    return Promise.reject(error)
  }
)

Vue.use(ElementUI)

new Vue({
  router,
  render: h => h(App),
}).$mount('#app')