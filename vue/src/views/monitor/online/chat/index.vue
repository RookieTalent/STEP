<template>
    <div class="app-container">
      <el-card class="box-card" style="border: 1px solid black">
        <div slot="header" class="clearfix">
          <h2><i class="el-icon-sunny"></i> 聊天室</h2>
        </div>
        <div v-for="o in 8" :key="o" class="text item">
          <div class="chat-container">
            <el-row>
              <el-col :span="1">
                <el-avatar :src="avatar"></el-avatar>
              </el-col>
              <el-col :span="23">
                <el-alert
                  style="width: 720px"
                  :title="nickname + ' | ' +new Date()"
                  type="success"
                  :closable="false"
                  :description="'这都是什么数字啊？ ' + o">
                </el-alert>
              </el-col>
            </el-row>
          </div>
        </div>
      </el-card>

      <el-input placeholder="请输入聊天信息" v-model="form" @keyup.enter.native="chat" >
        <template slot="prepend">{{nickname}}</template>
        <el-button slot="append" type="warning" icon="el-icon-chat-line-square" @click="chat"></el-button>
      </el-input>

    </div>
</template>

<script>
    import{connect, wsSend} from '@/utils/chat'
    import store from '@/store'


    export default {
      name: "index",
      data(){
          return{
            // 聊天信息
            form:"",
            // 测试头像
            avatar: store.getters.avatar,
            nickname: store.getters.name,
            type:["success","info","warning","error"],
            socket: null,
            receive: ""
          }
      },

      created() {
        this.init();
      },

      methods:{
        getType(){
          var index = Math.ceil(Math.random()*(3-(-1)))+(-1);
          return index;
        },

        init(){
          alert("待完成!");
        }

        /*init(){
          if(window.WebSocket){
            this.socket = new WebSocket("ws://127.0.0.1:8082/ws");
            this.socket.onopen = this.socketonOpen();
            this.socket.onclose = this.socketonClose();
            this.socket.onerror = this.socketonError();
            this.socket.onmessage = this.socketonMessage();
          }else{
            console.log("您的浏览器不支持websocket协议");
          }
        },

        chat(){
          this.socket.send(JSON.stringify(this.form));
        },

        socketonOpen(){
          console.log("建立成功");
        },

        socketonClose(){
          console.log("链接关闭");
        },

        socketonError(){
          console.log("发生异常");
        },

        socketonMessage(event){
          console.log("接收消息" + event);
          this.receive = event;
        },*/

      }
    }
</script>

<style scoped>
  .chat-container{
    margin: 20px;
  }
</style>
