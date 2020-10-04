import Vue from 'vue'

import Cookies from 'js-cookie'

import 'normalize.css/normalize.css' // A modern alternative to CSS resets

import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import locale from 'element-ui/lib/locale/lang/en' // lang i18n
import {resetForm, handleTree, download, buildUUID, addDateRange} from "@/utils/step";
import { getDicts } from "@/api/system/dict/data";

import '@/styles/index.scss' // global css

import App from './App'
import router from './router'
import store from './store'

import '@/icons' // icon
import '@/permission' // permission control

// 全局方法挂载
Vue.prototype.getDicts = getDicts;
Vue.prototype.download = download;
Vue.prototype.handleTree = handleTree;
Vue.prototype.resetForm = resetForm;
Vue.prototype.buildUUID = buildUUID;
Vue.prototype.addDateRange = addDateRange;

Vue.use(ElementUI, { locale })

Vue.config.productionTip = false

new Vue({
  el: '#app',
  router,
  store,
  render: h => h(App)
})
