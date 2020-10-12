import request from '@/utils/request'

export default {

  // 分页列表查询
  listRole(query){
    return request({
      url: 'system/role/list',
      method: 'post',
      data: query
    })
  },

  // 根据id查询角色
  getRole(id){
    return request({
      url: `/system/role/${id}`,
      method: 'get'
    })
  },

  // 新增角色
  addRole(data){
    return request({
      url: '/system/role',
      method: 'post',
      data: data
    })
  },

  // 角色数据权限
  dataScope(data){
    return request({
      url: '/system/role/dataScope',
      method: 'put',
      data: data
    })
  },

  // 修改角色
  updateRole(data){
    return request({
      url: '/system/role',
      method: 'put',
      data: data
    })
  },

  // 删除角色
  delRole(roleId){
    return request({
      url: '/system/role/' + roleId,
      method: 'delete'
    })
  }

}
