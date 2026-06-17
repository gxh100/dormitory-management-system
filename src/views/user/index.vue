<template>
  <div style="padding:20px;">
    <el-card shadow="never">
      <div slot="header">用户管理</div>
      <el-row :gutter="20">
        <el-col :span="6">
          <el-input v-model="username" placeholder="用户名（模糊）" clearable></el-input>
        </el-col>
        <el-col :span="6">
          <el-select v-model="flag" placeholder="职务" clearable style="width:100%">
            <el-option label="宿舍管理员" value="0"></el-option>
            <el-option label="系统管理员" value="1"></el-option>
          </el-select>
        </el-col>
        <el-col :span="12">
          <el-button type="primary" @click="search(1)">搜索</el-button>
          <el-button @click="resetSearch">重置</el-button>
          <el-button type="success" @click="addUser">新增</el-button>
        </el-col>
      </el-row>
      <el-table :data="tableData" border style="margin-top:20px;">
        <el-table-column prop="id" label="编号" width="80"></el-table-column>
        <el-table-column prop="username" label="用户名"></el-table-column>
        <el-table-column prop="password" label="密码"></el-table-column>
        <el-table-column prop="name" label="真实姓名"></el-table-column>
        <el-table-column prop="gender" label="性别"></el-table-column>
        <el-table-column prop="telephone" label="联系电话"></el-table-column>
        <el-table-column prop="flag" label="职务">
          <template slot-scope="scope">
            <span v-if="scope.row.flag == 1">系统管理员</span>
            <span v-else>宿舍管理员</span>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="150">
          <template slot-scope="scope">
            <el-button type="text" @click="editHandle(scope.row)">编辑</el-button>
            <el-button type="text" style="color:red" @click="deleteHandle(scope.$index, scope.row.id)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
      <el-pagination
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page="currentPage"
        :page-sizes="[5,10,20]"
        :page-size="pageSize"
        layout="total, sizes, prev, pager, next"
        :total="total"
        style="margin-top:20px;">
      </el-pagination>
    </el-card>

    <el-dialog :title="title" :visible.sync="dialogVisible" width="500px">
      <el-form :model="ruleForm" label-width="100px">
        <el-form-item label="用户名">
          <el-input v-model="ruleForm.username"></el-input>
        </el-form-item>
        <el-form-item label="密码">
          <el-input v-model="ruleForm.password" type="password"></el-input>
        </el-form-item>
        <el-form-item label="真实姓名">
          <el-input v-model="ruleForm.name"></el-input>
        </el-form-item>
        <el-form-item label="性别">
          <el-radio-group v-model="ruleForm.gender">
            <el-radio label="男">男</el-radio>
            <el-radio label="女">女</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="手机号">
          <el-input v-model="ruleForm.telephone"></el-input>
        </el-form-item>
        <el-form-item label="职务">
          <el-select v-model="ruleForm.flag" placeholder="请选择职务">
            <el-option label="宿舍管理员" value="0"></el-option>
            <el-option label="系统管理员" value="1"></el-option>
          </el-select>
        </el-form-item>
      </el-form>
      <span slot="footer">
        <el-button @click="dialogVisible=false">取消</el-button>
        <el-button type="primary" @click="submitUser">确定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
export default {
  data() {
    return {
      username: '',
      flag: null,
      tableData: [],
      currentPage: 1,
      pageSize: 5,
      total: 0,
      dialogVisible: false,
      title: '',
      ruleForm: {
        id: null,
        username: '',
        password: '',
        name: '',
        gender: '',
        telephone: '',
        flag: '',
        oldUserName: ''
      }
    }
  },
  mounted() {
    this.search(1)
  },
  methods: {
    search(page) {
      this.currentPage = page
      const params = {
        username: this.username,
        flag: this.flag !== '' ? parseInt(this.flag) : null,
        pageNo: this.currentPage,
        size: this.pageSize
      }
      this.$http.post('/user/getAllUsersByCon', params).then(res => {
        if (res.data.status) {
          this.tableData = res.data.data.data
          this.total = res.data.data.count
        } else {
          this.$message.error(res.data.message)
        }
      })
    },
    resetSearch() {
      this.username = ''
      this.flag = null
      this.search(1)
    },
    addUser() {
      this.ruleForm = { id: null, username: '', password: '', name: '', gender: '', telephone: '', flag: '', oldUserName: '' }
      this.title = '新增用户'
      this.dialogVisible = true
    },
    editHandle(row) {
      this.ruleForm = { ...row, oldUserName: row.username }
      this.ruleForm.flag = String(row.flag)
      this.title = '编辑用户'
      this.dialogVisible = true
    },
    submitUser() {
      const url = this.ruleForm.id ? '/user/updateUser' : '/user/addUser'
      this.$http.post(url, this.ruleForm).then(res => {
        if (res.data.status) {
          this.$message.success(res.data.message)
          this.dialogVisible = false
          this.search(this.currentPage)
        } else {
          this.$message.error(res.data.message)
        }
      })
    },
    deleteHandle(index, id) {
      this.$confirm('确定删除该用户吗？', '提示', { type: 'warning' }).then(() => {
        this.$http.post('/user/delUserById', null, { params: { userId: id } }).then(res => {
          if (res.data.status) {
            this.$message.success('删除成功')
            this.tableData.splice(index, 1)
            if (this.tableData.length === 0 && this.currentPage > 1) {
              this.search(this.currentPage - 1)
            } else {
              this.search(this.currentPage)
            }
          } else {
            this.$message.error(res.data.message)
          }
        })
      }).catch(() => {})
    },
    handleSizeChange(size) {
      this.pageSize = size
      this.search(1)
    },
    handleCurrentChange(page) {
      this.search(page)
    }
  }
}
</script>