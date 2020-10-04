<template>
    <div class="app-container">
      <el-row :gutter="20">
        <el-col :span="6" :xs="24">
          <el-card class="card-box">
            <div slot="header">
              <span>个人信息</span>
            </div>
            <div>
              <!--头像-->
              <div style="text-align: center">
                <userAvatar :user="user"/>
              </div>
              <!--TODO 图标问题-->
              <ul class="list-group list-group-striped">
                <li class="list-group-item">
                  <svg-icon icon-class="user" />用户名称
                  <div class="pull-right">{{ user.nickname }}</div>
                </li>
                <li class="list-group-item">
                  <svg-icon icon-class="user" />手机号码
                  <div class="pull-right">{{ user.mobile }}</div>
                </li>
                <li class="list-group-item">
                  <svg-icon icon-class="user" />用户邮箱
                  <div class="pull-right">{{ user.email }}</div>
                </li>
                <li class="list-group-item">
                  <svg-icon icon-class="tree" />所属学院
                  <div class="pull-right">信息学院</div>
                </li>
                <li class="list-group-item">
                  <svg-icon icon-class="user" />所属角色
                  <div class="pull-right">管理员</div>
                </li>
                <li class="list-group-item">
                  <svg-icon icon-class="user" />创建日期
                  <div class="pull-right">{{ user.gmtCreate }}</div>
                </li>
              </ul>
            </div>
          </el-card>
        </el-col>

        <el-col :span="18" :xs="24">
          <el-card>
            <div slot="header">
              <span>基本资料</span>
            </div>
            <el-tabs v-model="activeTab">
              <el-tab-pane label="基本资料" name="userinfo">
                <userInfo :user="user"/>
              </el-tab-pane>
              <el-tab-pane label="修改密码" name="resetPwd">
                <resetPwd :user="user"/>
              </el-tab-pane>
            </el-tabs>
          </el-card>
        </el-col>
      </el-row>
    </div>
</template>

<script>
    import userAvatar from "./userAvatar";
    import userInfo from "./userInfo";
    import resetPwd from "./resetPwd";
    import UserApi from '@/api/system/user';

    export default {
      name: "index",
      components: { userAvatar, userInfo, resetPwd },
      data(){
        return{
          user:{},
          activeTab: "userinfo"
        }
      },

      created() {
        this.getUser();
      },

      methods:{
        getUser(){
          UserApi.getUserProfile().then(response =>{
            this.user = response.data.userInfo;
            // TODO 注意后期需要给权限组 和 角色组赋值
          });
        }
      }
    }
</script>

<style scoped>
  .card-box {
    padding-right: 15px;
    padding-left: 15px;
    margin-bottom: 10px;
  }

  .list-group {
    padding-left: 0px;
    list-style: none;
  }

  .list-group-item {
    border-bottom: 1px solid #e7eaec;
    border-top: 1px solid #e7eaec;
    margin-bottom: -1px;
    padding: 11px 0px;
    font-size: 13px;
  }

  .list-group-striped > .list-group-item {
    border-left: 0;
    border-right: 0;
    border-radius: 0;
    padding-left: 0;
    padding-right: 0;
  }

  .pull-right {
    float: right !important;
  }

</style>
