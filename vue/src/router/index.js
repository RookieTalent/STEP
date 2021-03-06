import Vue from 'vue'
import Router from 'vue-router'

// in development-env not use lazy-loading, because lazy-loading too many pages will cause webpack hot update too slow. so only in production use lazy-loading;
// detail: https://panjiachen.github.io/vue-element-admin-site/#/lazy-loading

Vue.use(Router)

/* Layout */
import Layout from '../views/layout/Layout'

/**
* hidden: true                   if `hidden:true` will not show in the sidebar(default is false)
* alwaysShow: true               if set true, will always show the root menu, whatever its child routes length
*                                if not set alwaysShow, only more than one route under the children
*                                it will becomes nested mode, otherwise not show the root menu
* redirect: noredirect           if `redirect:noredirect` will no redirect in the breadcrumb
* name:'router-name'             the name is used by <keep-alive> (must set!!!)
* meta : {
    title: 'title'               the name show in submenu and breadcrumb (recommend set)
    icon: 'svg-name'             the icon show in the sidebar,
  }
**/
export const constantRouterMap = [
  { path: '/login', component: () => import('@/views/login/index'), hidden: true },
  { path: '/404', component: () => import('@/views/404'), hidden: true },

  {
    path: '/',
    component: Layout,
    redirect: '/dashboard',
    name: 'Dashboard',
    hidden: true,
    children: [{
      path: 'dashboard',
      component: () => import('@/views/dashboard/index')
    }]
  },

  {
    path: '/system',
    component: Layout,
    redirect: 'noredirect',
    alwaysShow: true,
    meta: { title: '系统管理', icon: 'example' },
    children: [
      {
        path: '/user',
        name: '用户管理',
        component: () => import('@/views/system/user/index'),
        meta: { title: '用户管理', icon: 'table' }
      },
      {
        path: '/college',
        name: '学院管理',
        component: () => import('@/views/system/college/index'),
        meta: { title: '学院管理', icon: 'table' }
      },
      {
        path: '/menu',
        name: '菜单管理',
        component: () => import('@/views/system/menu/index'),
        meta: { title: '菜单管理', icon: 'table' }
      },
      {
        path: '/course',
        name: '课程管理',
        component: () => import('@/views/system/course/index'),
        meta: { title: '课程管理', icon: 'table' }
      },
      {
        path: '/dict',
        name: '字典管理',
        component: () => import('@/views/system/dict/index'),
        meta: { title: '字典数据', icon: 'table' },
      },
      {
        path: '/role',
        name: '角色管理',
        component: () => import('@/views/system/role/index'),
        meta: { title: '角色管理', icon: 'table' },
      },
      {
        path: '/notice',
        name: '公告通知',
        component: () => import('@/views/system/notice/index'),
        meta: { title: '公告通知', icon: 'table' },
      },
      {
        path: '/log',
        name: '日志管理',
        component: () => import('@/views/system/log/index'),
        meta: { title: '日志管理', icon: 'table' },
      }
    ]
  },

  {
    path: '/monitor',
    component: Layout,
    redirect: 'noredirect',
    alwaysShow: true,
    meta: {title: '系统监控', icon: 'link'},
    children: [
      {
        path: '/online',
        name: '在线用户',
        component: () => import('@/views/monitor/online/index'),
        meta: { title: '在线用户', icon: 'table' }
      },
      {
        path: 'http://localhost:8081/druid/index.html',
        meta: { title: '数据监控', icon: 'link' }
      }
    ]
  },



  // 数字字典数据跳转页面
  {
    path: '/dict',
    component: Layout,
    hidden: true,
    children: [
      {
        path: 'type/data/:dictId(\\d+)',
        component: (resolve) => require(['@/views/system/dict/data'], resolve),
        name: 'Data',
        meta: { title: '字典数据', icon: '' }
      }
    ]
  },

  // 个人主页
  {
    path: '/user',
    component: Layout,
    hidden: true,
    redirect: 'noredirect',
    children: [
      {
        path: 'profile',
        component: (resolve) => require(['@/views/system/user/profile/index'], resolve),
        name: 'Profile',
        meta: { title: '个人中心', icon: '' }
      }
    ]
  },

  // 聊天室
  {
    path: '/online',
    component: Layout,
    hidden: true,
    redirect: 'noredirect',
    children: [
      {
        path: 'chat',
        component: (resolve) => require(['@/views/monitor/online/chat/index'], resolve),
        name: 'Profile',
        meta: { title: '个人中心', icon: '' }
      }
    ]
  },

  {
    path: 'external-link',
    component: Layout,
    children: [
      {
        path: 'https://github.com/RookieTalent/STEP',
        meta: { title: 'Step源码', icon: 'link' }
      }
    ]
  },

  { path: '*', redirect: '/404', hidden: true }
]

export default new Router({
  mode: 'history', //后端支持可开
  scrollBehavior: () => ({ y: 0 }),
  routes: constantRouterMap
})
