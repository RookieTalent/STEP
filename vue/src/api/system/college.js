import request from '@/utils/request'

export default {

  // 查询学院列表
  listCollege(query){
    return request({
      url:'/system/college/list',
      method: 'post',
      data: query
    });
  },

  // 查询学院下拉树结构
  treeselect(){
    return request({
      url: '/system/college/treeselect',
      method: 'get'
    });
  },

  // 新增学院信息
  addCollege(data){
    return request({
      url: '/system/college',
      method: 'post',
      data: data
    })
  },

  // 根据id查询学院详细
  getCollege(id){
    return request({
      url: `/system/college/${id}`,
      method: 'get'
    })
  },

  // 修改学院信息
  updateCollege(data){
    return request({
      url: '/system/college',
      method: 'put',
      data: data
    })
  },

  // 删除学院信息
  deleteCollege(id){
    return request({
      url: `/system/college/${id}`,
      method: 'delete'
    })
  },

  // 根据角色id查询学院树结构
  roleCollegeTreeselect(id){
    return request({
      url: '/system/college/roleDeptTreeselect/' + id,
      method: 'get'
    })
  }

}
