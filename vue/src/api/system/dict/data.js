import request from '@/utils/request'

export default {
  // 查询字典数据列表
  listData(query){
    return request({
      url: '/system/dict/data/list',
      method: 'get',
      params: query
    })
  },

  // 新增字典数据
  addData(data){
    return request({
      url: '/system/dict/data',
      method: 'post',
      data: data
    })
  },

  // 根据id查询字典数据
  getData(dictCode){
    return request({
      url: `/system/dict/data/${dictCode}`,
      method: 'get',
    })
  },

  // 修改字典数据
  updateData(data){
    return request({
      url: '/system/dict/data',
      method: 'put',
      data: data
    })
  },

  // 删除字典数据
  delData(dictCode){
    return request({
      url: '/system/dict/data/' + dictCode,
      method: 'delete'
    })
  },

  // 导出字典数据
  exportData(){
    return request({
      url: '/system/dict/data/export',
      method: 'get',
    })
  }
}

export function getDicts(dictType) {
  return request({
    url: '/system/dict/data/type/' + dictType,
    method: 'get'
  })
}
