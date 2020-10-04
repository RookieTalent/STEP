import request from '@/utils/request'


export default {

  //查询用户列表
  listUser(queryParams){
    return request({
      url:'/system/user/list',
      method:'post',
      data:queryParams
    })
  },

  //新增用户
  addUser(data){
    return request({
      url:'/system/user',
      method: 'post',
      data:data
    })
  },

  //查询用户详细
  getUser(id){
    return request({
      url:`/system/user/${id}`,
      method:'get'
    })
  },

  //更新用户信息
  updateUser(data){
    return request({
      url:'/system/user',
      method:'put',
      data:data
    })
  },

  //删除用户信息
  deleUser(ids, sms_code, sms_mobile){
    return request({
      url: `/system/user/${ids}/${sms_code}/${sms_mobile}`,
      method:'delete'
    })
  },

  //从token中获取到登录用户的手机号
  getCode(){
    return request({
      url:'/captcha',
      method:'get'
    })
  },


  //下载用户导入模板
  importTemplate(){
    return request({
      url: '/system/user/importTemplate',
      method: 'get'
    })
  },

  //从数据库中导出用户数据信息
  exportUser(query){
    return request({
      url:'/system/user/export',
      method: 'get',
      data: query
    })
  },

  //获取用户下拉菜单
  treeselect(){
    return request({
      url:'/system/user/treeselect',
      method: 'get'
    })
  },

  // 查询用户个人信息
  getUserProfile(){
    return request({
      url: '/system/user/profile',
      method: 'get'
    })
  },


}
