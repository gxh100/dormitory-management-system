<template>
  <div style="padding:20px;">
    <el-card shadow="never">
      <div slot="header">宿舍管理</div>
      <el-row :gutter="20">
        <el-col :span="6">
          <el-input v-model="searchBuildingId" placeholder="楼宇ID（精确）" clearable></el-input>
        </el-col>
        <el-col :span="6">
          <el-input v-model="searchName" placeholder="宿舍编号（模糊）" clearable></el-input>
        </el-col>
        <el-col :span="12">
          <el-button type="primary" @click="search(1)">搜索</el-button>
          <el-button @click="resetSearch">重置</el-button>
          <el-button type="success" @click="addDialog">新增</el-button>
        </el-col>
      </el-row>
      <el-table :data="tableData" border style="margin-top:20px;">
        <el-table-column prop="id" label="ID" width="80"></el-table-column>
        <el-table-column prop="buildingId" label="楼宇ID"></el-table-column>
        <el-table-column prop="name" label="宿舍编号"></el-table-column>
        <el-table-column prop="type" label="类型（人数）"></el-table-column>
        <el-table-column prop="available" label="剩余床位"></el-table-column>
        <el-table-column prop="telephone" label="联系电话"></el-table-column>
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
        <el-form-item label="楼宇ID">
          <el-input v-model="form.buildingId" placeholder="数字"></el-input>
        </el-form-item>
        <el-form-item label="宿舍编号">
          <el-input v-model="form.name" placeholder="例如 101"></el-input>
        </el-form-item>
        <el-form-item label="类型（人数）">
          <el-input v-model="form.type" placeholder="4/6/8"></el-input>
        </el-form-item>
        <el-form-item label="剩余床位">
          <el-input v-model="form.available" placeholder="0"></el-input>
        </el-form-item>
        <el-form-item label="联系电话">
          <el-input v-model="form.telephone" placeholder="宿舍电话"></el-input>
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
      searchBuildingId: '',
      searchName: '',
      tableData: [],
      currentPage: 1,
      pageSize: 5,
      total: 0,
      dialogVisible: false,
      dialogTitle: '',
      form: {
        id: null,
        buildingId: '',
        name: '',
        type: '',
        available: '',
        telephone: ''
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
        buildingId: this.searchBuildingId ? parseInt(this.searchBuildingId) : null,
        name: this.searchName,
        pageNo: this.currentPage,
        size: this.pageSize
      }
      this.$http.post('/dormitory/getAllByCon', params).then(res => {
        if (res.data.status) {
          this.tableData = res.data.data.data
          this.total = res.data.data.count
        } else {
          this.$message.error(res.data.message)
        }
      })
    },
    resetSearch() {
      this.searchBuildingId = ''
      this.searchName = ''
      this.search(1)
    },
    addDialog() {
      this.form = { id: null, buildingId: '', name: '', type: '', available: '', telephone: '' }
      this.dialogTitle = '新增宿舍'
      this.dialogVisible = true
    },
    editDialog(row) {
      this.form = { ...row }
      this.dialogTitle = '编辑宿舍'
      this.dialogVisible = true
    },
    submit() {
      const url = this.form.id ? '/dormitory/update' : '/dormitory/add'
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
      this.$confirm('确定删除该宿舍吗？', '提示', { type: 'warning' }).then(() => {
        this.$http.post('/dormitory/delById', null, { params: { id } }).then(res => {
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