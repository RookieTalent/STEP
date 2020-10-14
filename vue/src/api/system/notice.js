import request from '@/utils/request'

export default {

  /*分页列表*/
  listNotice(query){
    return request({
      url: '/system/notice/list',
      method: 'post',
      data: query
    })
  },


  // 查询公告详细
  getNotice(id){
    return request({
      url: `/system/notice/${id}`,
      method: 'get'
    })
  },

  // 新增公告通知
  addNotice(data){
    return request({
      url: '/system/notice',
      method: 'post',
      data: data
    })
  },

  // 修改公告通知
  updateNotice(data){
    return request({
      url: '/system/notice',
      method: 'put',
      data: data
    })
  },

  //删除公告
  delNotice(noticeId){
    return request({
      url: '/system/notice/' + noticeId,
      method: 'delete'
    })
  },

  /** 播放公告*/
  playNotice(){
    return request({
      url: '/system/notice/play',
      method: 'get'
    })
  }


}
