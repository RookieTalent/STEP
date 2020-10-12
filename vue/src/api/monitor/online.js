import request from '@/utils/request'

export default {
  // 在线用户列表
  list(query){
    return request({
      url: '/monitor/online/list',
      method: 'get',
      params: query
    })
  },



}
