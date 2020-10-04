import request from '@/utils/request'

export default {

  // 菜单列表
  getList(query){
    return request({
      url:'/system/dict/type/list',
      method: 'get',
      params: query
    })
  },

  // 根据id查询
  getType(id){
    return request({
      url: `/system/dict/type/${id}`,
      method: 'get'
    })
  },

  // 新增字典
  addType(data){
    return request({
      url: '/system/dict/type',
      method: 'post',
      data: data
    })
  },

  // 修改字典
  updateType(data){
    return request({
      url: '/system/dict/type',
      method: 'put',
      data: data
    })
  },

  // 删除字典
  delType(dictId){
    return request({
      url: '/system/dict/type/' + dictId,
      method: 'delete'
    })
  },

  // 导出excel表
  exportType(){
    return request({
      url: '/system/dict/type/export',
      method: 'get',
    })
  },

  // 清空缓存
  clearCache(){
    return request({
      url: '/system/dict/type/clearCache',
      method: 'delete'
    })
  }

}
