<template>
  <div id="app">
    <!-- 仅在已登录时显示导航栏 -->
    <div v-if="isLoggedIn" class="navbar">
      <router-link to="/user">用户管理</router-link>
      <router-link to="/building">楼宇管理</router-link>
      <router-link to="/dormitory">宿舍管理</router-link>
      <router-link to="/student">学生管理</router-link>
      <router-link to="/absent">缺勤管理</router-link>
      <el-button type="text" @click="logout" style="color:white; margin-left:auto;">退出登录</el-button>
    </div>
    <router-view />
  </div>
</template>

<script>
export default {
  computed: {
    isLoggedIn() {
      return !!localStorage.getItem('token')
    }
  },
  methods: {
    logout() {
      localStorage.removeItem('token')
      this.$router.push('/login')
      this.$message.success('已退出登录')
    }
  }
}
</script>

<style>
* {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
}
body {
  background: #f0f2f5;
  font-family: 'Helvetica Neue', Helvetica, 'PingFang SC', 'Hiragino Sans GB', 'Microsoft YaHei', sans-serif;
}
.navbar {
  background: #409EFF;
  padding: 10px 20px;
  display: flex;
  gap: 20px;
  align-items: center;
}
.navbar a {
  color: white;
  text-decoration: none;
  font-size: 14px;
}
.navbar a:hover {
  text-decoration: underline;
}
</style>