import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'

import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import 'font-awesome/css/font-awesome.min.css'
import * as echarts from 'echarts'
import 'echarts/map/js/china'


Vue.use(echarts);
Vue.use(ElementUI);
//Vue.prototype.$echarts = echarts

Vue.config.productionTip = false;

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')