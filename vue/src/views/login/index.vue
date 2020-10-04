<template>
  <div class="login" style="background-color: #99a9bf">
    <el-form ref="loginForm" :model="loginForm" :rules="loginRules" class="login-form">
      <h3 class="title">STEP 管理系统</h3>

      <!--账号-->
      <el-form-item prop="username">
        <el-input v-model="loginForm.username" type="text" auto-complete="off" placeholder="账号">
          <svg-icon slot="prefix" icon-class="user" class="el-input__icon input-icon" />
        </el-input>
      </el-form-item>

      <!--密码-->
      <el-form-item prop="password">
        <el-input
          v-model="loginForm.password"
          type="password"
          auto-complete="off"
          placeholder="密码"
          @keyup.enter.native="handleLogin"
        >
          <svg-icon slot="prefix" icon-class="password" class="el-input__icon input-icon" />
        </el-input>
      </el-form-item>

      <!--验证码-->
      <el-form-item prop="code">
        <el-input
          v-model="loginForm.code"
          auto-complete="off"
          placeholder="验证码"
          style="width: 63%"
          @keyup.enter.native="handleLogin"
        >
          <svg-icon slot="prefix" icon-class="validCode" class="el-input__icon input-icon" />
        </el-input>
        <div class="login-code">
          <img :src="codeUrl" @click="getCode" />
        </div>
      </el-form-item>

      <!--登录按钮-->
      <el-form-item style="width:100%;">
        <el-button
          :loading="loading"
          size="medium"
          type="primary"
          style="width:100%;"
          @click.native.prevent="handleLogin"
        >
          <span v-if="!loading">登 录</span>
          <span v-else>登 录 中...</span>
        </el-button>
      </el-form-item>

      <!--登录按钮-->
      <el-form-item style="width:100%;">
        <el-button
          :loading="loading"
          size="medium"
          type="success"
          style="width:100%;"
          @click="toGithub"
        >
          <span v-if="!loading" >GitHub 登录</span>
          <span v-else>登 录 中...</span>
        </el-button>
      </el-form-item>

    </el-form>


    <!--  底部  -->
    <div class="el-login-footer">
      <span>Copyright © 2020-2021 rookietalent All Rights Reserved.</span>
    </div>
  </div>
</template>

<script>
  import {getCodeImg} from "@/api/login";


  export default {
    name: 'Login',
    data() {
      const validatePass = (rule, value, callback) => {
        if (value.length < 5) {
          callback(new Error('密码不能小于6位'))
        } else {
          callback()
        }
      }
      return {
        codeUrl: "",
        loginForm: {
          username: '13695740292',
          password: 'admin',
          code:'',
          uuid:"",
        },
        loginRules: {
          username: [{ required: true, trigger: 'blur', message: "用户名不能为空"}],
          password: [{ required: true, trigger: 'blur', validator: validatePass }],
          code: [{ required: true, trigger: "change", message: "验证码不能为空" }]
        },
        loading: false,
        pwdType: 'password',
        redirect: undefined
      }
    },

    watch: {
      $route: {
        handler: function(route) {
          this.redirect = route.query && route.query.redirect
        },
        immediate: true
      }
    },

    created(){
      this.getCode();
    },

    methods: {
      showPwd() {
        if (this.pwdType === 'password') {
          this.pwdType = ''
        } else {
          this.pwdType = 'password'
        }
      },

      handleLogin() {
        this.$refs.loginForm.validate(valid => {
          if (valid) {
            this.loading = true;
            this.$store.dispatch('Login', this.loginForm).then(() => {
              this.loading = false;
              this.$router.push({ path: this.redirect || '/' })
            }).catch(() => {
              this.loading = false;
              this.getCode();
            })
          } else {
            console.log('error submit!!');
            return false;
          }
        })
      },

      //获取验证码信息
      getCode() {
        getCodeImg().then(res => {
          this.codeUrl = "data:image/gif;base64," + res.data.img;
          this.loginForm.uuid = res.data.uuid;
        });
      },

      //TODO GitHub登录
      toGithub(){
        window.location.href = 'https://github.com/login/oauth/authorize?client_id=34eed6e5f9f733d240c2&redirect_uri=http://localhost:8081/callback&scope=user&state=1';
      }

    }
  }
</script>

<style rel="stylesheet/scss" lang="scss">
  $bg:#2d3a4b;
  $light_gray:#eee;

  /* reset element-ui css */
  .login-container {
    .el-input {
      display: inline-block;
      height: 47px;
      width: 85%;
      input {
        background: transparent;
        border: 0px;
        -webkit-appearance: none;
        border-radius: 0px;
        padding: 12px 5px 12px 15px;
        color: $light_gray;
        height: 47px;
        &:-webkit-autofill {
          -webkit-box-shadow: 0 0 0px 1000px $bg inset !important;
          -webkit-text-fill-color: #fff !important;
        }
      }
    }
    .el-form-item {
      border: 1px solid rgba(255, 255, 255, 0.1);
      background: rgba(0, 0, 0, 0.1);
      border-radius: 5px;
      color: #454545;
    }
  }

</style>

<style rel="stylesheet/scss" lang="scss">
  .login {
    display: flex;
    justify-content: center;
    align-items: center;
    height: 100%;
    background-size: cover;
  }
  .title {
    margin: 0px auto 30px auto;
    text-align: center;
    color: #707070;
  }

  .login-form {
    border-radius: 6px;
    background: #ffffff;
    width: 400px;
    padding: 25px 25px 5px 25px;
    .el-input {
      height: 38px;
      input {
        height: 38px;
      }
    }
    .input-icon {
      height: 39px;
      width: 14px;
      margin-left: 2px;
    }
  }
  .login-tip {
    font-size: 13px;
    text-align: center;
    color: #bfbfbf;
  }
  .login-code {
    width: 33%;
    height: 38px;
    float: right;
    img {
      cursor: pointer;
      vertical-align: middle;
    }
  }
  .el-login-footer {
    height: 40px;
    line-height: 40px;
    position: fixed;
    bottom: 0;
    width: 100%;
    text-align: center;
    color: #fff;
    font-family: Arial;
    font-size: 12px;
    letter-spacing: 1px;
  }
</style>
