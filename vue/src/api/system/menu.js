import request from '@/utils/request'

export default {

  // 查询菜单全部列表
  listMenu(data){
    return request({
      url: '/system/menu/list',
      method: 'post',
      data: data
    })
  },

  // 下拉树菜单
  treeselect(){
    return request({
      url:  '/system/menu/treeselect',
      method: 'get'
    })
  },

  // 菜单查询
  getMenu(id){
    return request({
      url: `/system/menu/${id}`,
      method: 'get'
    })
  },

  // 新增菜单
  addMenu(data){
    return request({
      url: '/system/menu',
      method: 'post',
      data: data
    })
  },

  // 更新某菜单信息
  updateMenu(data){
    return request({
      url: '/system/menu',
      method: 'put',
      data: data
    })
  },

  // 删除某菜单信息
  delMenu(id){
    return request({
      url: `/system/menu/${id}`,
      method: 'delete'
    })
  }


}
