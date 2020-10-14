<template>
  <div class="dashboard-editor-container" >

    <el-row :gutter="32">
      <el-col :xs="24" :sm="24" :lg="8">
        <div class="chart-wrapper">
          <raddar-chart />
        </div>
      </el-col>
      <el-col :xs="24" :sm="24" :lg="8">
        <div class="chart-wrapper">
          <pie-chart/>
        </div>
      </el-col>
      <el-col :xs="24" :sm="24" :lg="8">
        <div class="chart-wrapper">
          <bar-chart/>
        </div>
      </el-col>
    </el-row>

    <el-row>
      <el-col :span="12">
        <el-card class="box-card">
          <div slot="header" class="clearfix">
            <h3><i class="el-icon-s-tools"></i> 登录记录</h3>
          </div>
          <div>
            <el-table :data="loginInfoList" style="width: 100%">
              <el-table-column label="用户" prop="nickname"/>
              <el-table-column label="登录地点" prop="loginLocation"/>
              <el-table-column label="登录地点" prop="ipaddr"/>
              <el-table-column label="提示信息" prop="msg"/>
              <el-table-column label="登录时间">
                <template slot-scope="scope">
                  <span>{{scope.row.loginTime}}</span>
                </template>
              </el-table-column>
            </el-table>
          </div>
        </el-card>
      </el-col>
      <el-col :span="10" :offset="1">
        <el-card class="box-card">
          <div slot="header" class="clearfix">
            <h3><i class="el-icon-setting"></i> 记事本</h3>
          </div>
          <div>
            <el-input
              type="textarea"
              :autosize="{ minRows: 8, maxRows: 10}"
              placeholder="请输入内容"
              v-model="textarea">
            </el-input>
            <el-button type="primary" style="margin-top: 20px; text-align: right" round @click="submitMemo">保 存</el-button>
          </div>
        </el-card>
      </el-col>
    </el-row>

  </div>
</template>

<script>
import { mapGetters } from 'vuex';
import NoticeApi from '@/api/system/notice';
import LogApi from '@/api/system/operlog';
import RaddarChart from './RaddarChart'
import PieChart from './PieChart'
import BarChart from './BarChart'
import LineChart from './LineChart'

export default {
  name: 'Dashboard',
  components: {LineChart, BarChart, PieChart, RaddarChart},
  computed: {
    ...mapGetters([
      'name',
      'roles',
      'permissions',
    ])
  },

  data(){
    return{
      // 播报通知
      noticeList: [],
      // 备忘文本
      textarea: "",
      loginInfoList: []
    }
  },

 created() {
    this.initNotice();
    this.getLoginInfo();
 },

  methods:{
    /** 初始化播报公告*/
    initNotice(){
      NoticeApi.playNotice().then(response =>{
        this.noticeList = response.data.list;
        for (var i = 0; i < this.noticeList.length; i++) {
          this.$notify({type: 'success', dangerouslyUseHTMLString: true, title: this.noticeList[i].noticeTitle, message: this.noticeList[i].noticeContent});
        }
      });
    },

    submitMemo(){
      this.$notify({type: 'warning', title: '提示', message: '功能未完善'});
    },

    /** 查询登录日志*/
    getLoginInfo(){
      LogApi.getLoginInfo().then(response =>{
        this.loginInfoList = response.data.list;
      })
    }


  }
}
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
.dashboard {
  &-container {
    margin: 30px;
  }
  &-text {
    font-size: 30px;
    line-height: 46px;
  }
}

.dashboard-editor-container {
  padding: 32px;
  background-color: rgb(240, 242, 245);
  position: relative;

  .chart-wrapper {
    background: #fff;
    padding: 16px 16px 0;
    margin-bottom: 32px;
  }
}

@media (max-width:1024px) {
  .chart-wrapper {
    padding: 8px;
  }
}
</style>
