<template>
  <div class="login-container">
    <el-card class="login-card">
      <h2 style="text-align:center;">高校宿舍管理系统</h2>
      <el-form :model="loginForm" :rules="rules" ref="loginForm" label-width="0px">
        <el-form-item prop="username">
          <el-input
            v-model="loginForm.username"
            placeholder="用户名"
            prefix-icon="el-icon-user"
            size="medium"
          ></el-input>
        </el-form-item>
        <el-form-item prop="password">
          <el-input
            v-model="loginForm.password"
            type="password"
            placeholder="密码"
            prefix-icon="el-icon-lock"
            size="medium"
            @keyup.enter.native="submitLogin"
          ></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" style="width:100%;" @click="submitLogin" size="medium">登录</el-button>
        </el-form-item>
        <div style="color:#aaa; font-size:12px; text-align:center;">提示：用户名密码均为 admin</div>
      </el-form>
    </el-card>
  </div>
</template>

<script>
export default {
  data() {
    return {
      loginForm: { username: '', password: '' },
      rules: {
        username: [{ required: true, message: '请输入用户名', trigger: 'blur' }],
        password: [{ required: true, message: '请输入密码', trigger: 'blur' }]
      }
    }
  },
  methods: {
    submitLogin() {
      this.$refs.loginForm.validate(valid => {
        if (valid) {
          this.$http.post('/user/login', this.loginForm).then(res => {
            if (res.data.status) {
              localStorage.setItem('token', res.data.data)
              this.$http.defaults.headers.common['Authorization'] = res.data.data
              this.$router.push('/user')
              this.$message.success('登录成功')
            } else {
              this.$message.error(res.data.message)
            }
          }).catch(() => {
            this.$message.error('登录失败，请稍后重试')
          })
        }
      })
    }
  }
}
</script>

<style scoped>
.login-container {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
  background: linear-gradient(135deg, #2d8cf0 0%, #0077b3 100%);
}
.login-card {
  width: 400px;
  border-radius: 8px;
  box-shadow: 0 2px 12px 0 rgba(0,0,0,0.2);
}
</style>