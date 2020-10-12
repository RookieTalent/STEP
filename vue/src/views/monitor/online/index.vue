<template>
    <div class="app-container">
      <el-form :model="queryParams" ref="queryForm" :inline="true">
        <el-form-item label="登录地址" prop="ipaddr">
          <el-input
            v-model="queryParams.ipaddr"
            placeholder="请输入登录地址"
            clearable
            size="small"
          />
        </el-form-item>
        <el-form-item label="用户名称" prop="userName">
          <el-input
            v-model="queryParams.userName"
            placeholder="请输入用户名称"
            clearable
            size="small"
          />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
          <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
          <router-link class="inlineBlock" to="/online/chat">
            <el-button icon="el-icon-user" size="mini" type="success" style="margin-left: 15px">进入聊天室</el-button>
          </router-link>
        </el-form-item>
      </el-form>

      <!--TODO list-->
      <el-table v-loading="loading"
                :data="list" style="width: 100%">
        <el-table-column label="序号" type="index" align="center">
          <template slot-scope="scope">
            <span>{{(pageNum - 1) * pageSize + scope.$index + 1}}</span>
          </template>
        </el-table-column>
        <el-table-column label="会话编号" align="center" prop="tokenId" :show-overflow-tooltip="true" />
        <el-table-column label="登录名称" align="center" prop="nickname" :show-overflow-tooltip="true" />
        <el-table-column label="学院名称" align="center" prop="collegeName">
          <template slot-scope="scope">
            <span>{{ scope.row.collegeName === null ? '目前还未完善' : '目前还未完善'}}</span>
          </template>
        </el-table-column>
        <el-table-column label="主机" align="center" prop="ipaddr" :show-overflow-tooltip="true" />
        <el-table-column label="登录地点" align="center" prop="loginLocation" :show-overflow-tooltip="true" />
        <el-table-column label="浏览器" align="center" prop="browser"/>
        <el-table-column label="操作系统" align="center" prop="os"/>
        <el-table-column label="登录时间" align="center" prop="loginTime" width="180">
          <template slot-scope="scope">
            <span>{{scope.row.loginTime}}</span>
          </template>
        </el-table-column>
        <el-table-column label="操作" align="center">
          <template slot-scope="scope">
            <el-button
              size="mini"
              type="text"
              icon="el-icon-delete"
            >强退</el-button>
          </template>
        </el-table-column>
      </el-table>

      <el-pagination v-show="total>0" :total="total" :page.sync="pageNum" :limit.sync="pageSize" style="margin-top: 20px"/>
    </div>
</template>

<script>
    import OnlineApi from '@/api/monitor/online'

    export default {
      name: "index",
      data(){
        return{
          //TODO 遮罩层
          loading: true,
          // 总条数
          total: 0,
          // 表格数据
          list: [],
          pageNum: 1,
          pageSize: 10,
          // 查询参数
          queryParams: {
            ipaddr: undefined,
            userName: undefined
          }
        }
      },

      created() {
        this.getList();
      },

      methods:{
        /** 查询列表*/
        getList(){
          this.loading = true;
          OnlineApi.list(this.queryParams).then(response =>{
            this.list = response.rows;
            this.total = response.total;
            this.loading = false;
          })
        },

        /** 搜索按钮操作 */
        handleQuery() {
          this.pageNum = 1;
          this.getList();
        },

        /** 重置按钮操作 */
        resetQuery() {
          this.resetForm("queryForm");
          this.handleQuery();
        },

      }

    }
</script>

<style scoped>

</style>
