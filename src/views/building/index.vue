<template>
  <div style="padding:20px;">
    <el-card shadow="never">
      <div slot="header">楼宇管理</div>
      <el-row :gutter="20">
        <el-col :span="6">
          <el-input v-model="searchName" placeholder="楼宇名称" clearable></el-input>
        </el-col>
        <el-col :span="6">
          <el-input v-model="searchAdminId" placeholder="管理员ID" clearable></el-input>
        </el-col>
        <el-col :span="12">
          <el-button type="primary" @click="search(1)">搜索</el-button>
          <el-button @click="resetSearch">重置</el-button>
          <el-button type="success" @click="addDialog">新增</el-button>
        </el-col>
      </el-row>
      <el-table :data="tableData" border style="margin-top:20px;">
        <el-table-column prop="id" label="ID" width="80"></el-table-column>
        <el-table-column prop="name" label="楼宇名称"></el-table-column>
        <el-table-column prop="introduction" label="介绍"></el-table-column>
        <el-table-column prop="adminId" label="管理员ID"></el-table-column>
        <el-table-column label="操作" width="150">
          <template slot-scope="scope">
            <el-button type="text" @click="editDialog(scope.row)">编辑</el-button>
            <el-button type="text" style="color:red" @click="del(scope.row.id)">删除</el-button>
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

    <el-dialog :title="dialogTitle" :visible.sync="dialogVisible" width="500px">
      <el-form :model="form" label-width="100px">
        <el-form-item label="楼宇名称">
          <el-input v-model="form.name"></el-input>
        </el-form-item>
        <el-form-item label="介绍">
          <el-input type="textarea" v-model="form.introduction"></el-input>
        </el-form-item>
        <el-form-item label="管理员ID">
          <el-input v-model="form.adminId"></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer">
        <el-button @click="dialogVisible=false">取消</el-button>
        <el-button type="primary" @click="submit">确定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
export default {
  data() {
    return {
      searchName: '',
      searchAdminId: '',
      tableData: [],
      currentPage: 1,
      pageSize: 5,
      total: 0,
      dialogVisible: false,
      dialogTitle: '',
      form: { id: null, name: '', introduction: '', adminId: '' }
    }
  },
  mounted() {
    this.search(1)
  },
  methods: {
    search(page) {
      this.currentPage = page
      const params = {
        name: this.searchName,
        adminId: this.searchAdminId ? parseInt(this.searchAdminId) : null,
        pageNo: this.currentPage,
        size: this.pageSize
      }
      this.$http.post('/building/getAllByCon', params).then(res => {
        if (res.data.status) {
          this.tableData = res.data.data.data
          this.total = res.data.data.count
        } else {
          this.$message.error(res.data.message)
        }
      })
    },
    resetSearch() {
      this.searchName = ''
      this.searchAdminId = ''
      this.search(1)
    },
    addDialog() {
      this.form = { id: null, name: '', introduction: '', adminId: '' }
      this.dialogTitle = '新增楼宇'
      this.dialogVisible = true
    },
    editDialog(row) {
      this.form = { ...row }
      this.dialogTitle = '编辑楼宇'
      this.dialogVisible = true
    },
    submit() {
      const url = this.form.id ? '/building/update' : '/building/add'
      this.$http.post(url, this.form).then(res => {
        if (res.data.status) {
          this.$message.success(res.data.message)
          this.dialogVisible = false
          this.search(this.currentPage)
        } else {
          this.$message.error(res.data.message)
        }
      })
    },
    del(id) {
      this.$confirm('确定删除该楼宇吗？', '提示', { type: 'warning' }).then(() => {
        this.$http.post('/building/delById', null, { params: { id } }).then(res => {
          if (res.data.status) {
            this.$message.success('删除成功')
            this.search(this.currentPage)
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