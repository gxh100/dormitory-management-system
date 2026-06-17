import Vue from 'vue'
import VueRouter from 'vue-router'
import Login from '@/views/Login.vue'
import UserManage from '@/views/user/index'
import BuildingManage from '@/views/building/index'
import DormitoryManage from '@/views/dormitory/index'
import StudentManage from '@/views/student/index'
import AbsentManage from '@/views/absent/index'

Vue.use(VueRouter)

const routes = [
  { path: '/login', name: 'Login', component: Login },
  {
    path: '/',
    redirect: '/user'
  },
  {
    path: '/user',
    name: 'User',
    component: UserManage,
    meta: { requiresAuth: true }
  },
  {
    path: '/building',
    name: 'Building',
    component: BuildingManage,
    meta: { requiresAuth: true }
  },
  {
    path: '/dormitory',
    name: 'Dormitory',
    component: DormitoryManage,
    meta: { requiresAuth: true }
  },
  {
    path: '/student',
    name: 'Student',
    component: StudentManage,
    meta: { requiresAuth: true }
  },
  {
    path: '/absent',
    name: 'Absent',
    component: AbsentManage,
    meta: { requiresAuth: true }
  }
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

// 路由守卫：未登录无法访问需要认证的页面
router.beforeEach((to, from, next) => {
  const token = localStorage.getItem('token')
  if (to.meta.requiresAuth) {
    if (token) {
      next()
    } else {
      next('/login')
    }
  } else {
    next()
  }
})

export default router