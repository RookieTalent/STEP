import request from '@/utils/request'

export default {

  // 查询日志全部列表
  listLog(data){
    return request({
      url: '/system/log/list',
      method: 'post',
      data: data
    })
  },

  // 删除日志操作
  delLog(operId){
    return request({
      url: '/system/log/' + operId,
      method: 'delete'
    })
  },

  // 清空日志操作
  cleanLog(){
    return request({
      url: '/system/log/clean',
      method: 'delete'
    })
  },

  // 导出excel表
  exportLog(){
    return request({
      url: '/system/log/export',
      method: 'get'
    })
  }


}
