<template>
  <div style="padding:20px;">
    <el-card shadow="never">
      <div slot="header">学生管理</div>
      <el-row :gutter="20">
        <el-col :span="5">
          <el-input v-model="searchName" placeholder="姓名（模糊）" clearable></el-input>
        </el-col>
        <el-col :span="5">
          <el-select v-model="searchGender" placeholder="性别" clearable style="width:100%">
            <el-option label="男" value="男"></el-option>
            <el-option label="女" value="女"></el-option>
          </el-select>
        </el-col>
        <el-col :span="5">
          <el-select v-model="searchState" placeholder="状态" clearable style="width:100%">
            <el-option label="入住" value="入住"></el-option>
            <el-option label="迁出" value="迁出"></el-option>
          </el-select>
        </el-col>
        <el-col :span="9">
          <el-button type="primary" @click="search(1)">搜索</el-button>
          <el-button @click="resetSearch">重置</el-button>
          <el-button type="success" @click="addDialog">新增</el-button>
        </el-col>
      </el-row>
      <el-table :data="tableData" border style="margin-top:20px;">
        <el-table-column prop="id" label="ID" width="80"></el-table-column>
        <el-table-column prop="number" label="学号"></el-table-column>
        <el-table-column prop="name" label="姓名"></el-table-column>
        <el-table-column prop="gender" label="性别"></el-table-column>
        <el-table-column prop="dormitoryId" label="宿舍ID"></el-table-column>
        <el-table-column prop="state" label="状态"></el-table-column>
        <el-table-column prop="createDate" label="登记日期" width="150"></el-table-column>
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
        <el-form-item label="学号">
          <el-input v-model="form.number"></el-input>
        </el-form-item>
        <el-form-item label="姓名">
          <el-input v-model="form.name"></el-input>
        </el-form-item>
        <el-form-item label="性别">
          <el-select v-model="form.gender" style="width:100%">
            <el-option label="男" value="男"></el-option>
            <el-option label="女" value="女"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="宿舍ID">
          <el-input v-model="form.dormitoryId" placeholder="数字"></el-input>
        </el-form-item>
        <el-form-item label="状态">
          <el-select v-model="form.state" style="width:100%">
            <el-option label="入住" value="入住"></el-option>
            <el-option label="迁出" value="迁出"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="登记日期">
          <el-input v-model="form.createDate" placeholder="例如 2025-09-29"></el-input>
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
      searchGender: '',
      searchState: '',
      tableData: [],
      currentPage: 1,
      pageSize: 5,
      total: 0,
      dialogVisible: false,
      dialogTitle: '',
      form: {
        id: null,
        number: '',
        name: '',
        gender: '',
        dormitoryId: '',
        state: '',
        createDate: ''
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
        name: this.searchName,
        gender: this.searchGender,
        state: this.searchState,
        pageNo: this.currentPage,
        size: this.pageSize
      }
      this.$http.post('/student/getAllByCon', params).then(res => {
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
      this.searchGender = ''
      this.searchState = ''
      this.search(1)
    },
    addDialog() {
      this.form = { id: null, number: '', name: '', gender: '', dormitoryId: '', state: '', createDate: '' }
      this.dialogTitle = '新增学生'
      this.dialogVisible = true
    },
    editDialog(row) {
      this.form = { ...row }
      this.dialogTitle = '编辑学生'
      this.dialogVisible = true
    },
    submit() {
      const url = this.form.id ? '/student/update' : '/student/add'
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
      this.$confirm('确定删除该学生吗？', '提示', { type: 'warning' }).then(() => {
        this.$http.post('/student/delById', null, { params: { id } }).then(res => {
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