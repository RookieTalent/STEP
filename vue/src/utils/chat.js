// 严格模式
'use strict';
import store from '@/store';

const enpoint = "/step";

var nickname = store.getters.name;
var stompClient = null;


/**
 * 连接服务器
 */
function connect() {
  var socket = new  SockJS(enpoint);
  stompClient = Stomp.over(socket);
  // 配置stomp
  config();
  // 订阅地址
  sub();
}

/**
 * 订阅地址
 */
function sub() {
  stompClient.connect(nickname, function (frame) {

  }, function (error) {
    alert("错误请刷新");

  })
}

/**
 * stomp配置
 */
function config() {
  // 每隔30秒一个心跳检测
  stompClient.heartbeat.outgoing = 30000;
  // 客户端不接受服务器的心跳检测
  stompClient.heartbeat.incoming = 0;
}


export {
  connect
}
