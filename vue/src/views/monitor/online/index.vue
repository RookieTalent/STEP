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
          <el-button type="primary" icon="el-icon-search" size="mini">搜索</el-button>
          <el-button icon="el-icon-refresh" size="mini">重置</el-button>
        </el-form-item>
      </el-form>

      <!--TODO 这里跟一个进入聊天室-->r

      <!--TODO 注意list -->
      <el-table v-loading="loading" :data="list" style="width: 100%">
        <!--TODO 序号要改-->
        <el-table-column label="序号" type="index" align="center">

        </el-table-column>
        <el-table-column label="会话编号" align="center" prop="tokenId" :show-overflow-tooltip="true" />
        <el-table-column label="登录名称" align="center" prop="userName" :show-overflow-tooltip="true" />
        <el-table-column label="学院名称" align="center" prop="collegeName"/>
        <el-table-column label="主机" align="center" prop="ipaddr" :show-overflow-tooltip="true" />
        <el-table-column label="登录地点" align="center" prop="loginLocation" :show-overflow-tooltip="true" />
        <el-table-column label="浏览器" align="center" prop="browser"/>
        <el-table-column label="操作系统" align="center" prop="os"/>
        <el-table-column label="登录时间" align="center" prop="loginTime" width="180">
          <!--TODO 写-->
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
    </div>
</template>

<script>
    export default {
      name: "index",
      data(){
        return{
          //TODO 遮罩层
          loading: false,
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
      },

      methods:{

      }

    }
</script>

<style scoped>

</style>
