// 严格模式
'use strict';
import store from '@/store';

const enpoint = "ws";

var nickname = store.getters.name;


/**
 * 连接服务器
 */
function connect(socket) {
  socket = new WebSocket("ws://127.0.0.1:8082/"+enpoint);
}

/**
 * 发布
 */
function wsSend(socket) {

}


export {
  connect
}
