/**
 * 通用js方法封装处理
 * Copyright (c) 2019 step
 */

const baseURL = process.env.BASE_API;


// 通用下载方式
export function download(fileName) {
  window.location.href = baseURL + "/common/download?fileName=" + encodeURI(fileName) + "&delete=" + true;
}

// 表单重置
export function resetForm(refName) {
  if(this.$refs[refName]){
    this.$refs[refName].resetFields();
  }
}

/**
 * 构建树形结构数据
 * @param data data数据源
 * @param id
 * @param parentId 父节点
 * @param children 孩子节点
 * @param rootId 根节点
 */
export function handleTree(data, id, parentId, children, rootId) {
  id = id || 'id';
  parentId = parentId || 'parentId';
  children = children || 'children';
  rootId = rootId || '0';
  // 对数据源进行深拷贝
  const cloneData = JSON.parse(JSON.stringify(data));

  // 循环所有项
  const treeData = cloneData.filter(father =>{
    let branchArr = cloneData.filter(child =>{
      // 返回每一项的子集数组
      return father[id] === child[parentId];
    });
    branchArr.length > 0 ? father.children = branchArr : '';
    // 返回第一层
    return father[parentId] === rootId;
  });

  return treeData != '' ? treeData : data;
}


/**
 * 生成UUID
 *
 * @returns {string}
 */
export function buildUUID() {
  var s = [];
  var hexDigits = "0123456789abcdef";
  for (var i = 0; i< 36; i++){
    s[i] = hexDigits.substr(Math.floor(Math.random() * 0x10), 1);
  }
  s[14] = "4";
  s[19] = hexDigits.substr((s[19] & 0x3) | 0x8, 1);
  s[8] = s[13] = s[18] = s[23] = "-";

  var uuid = s.join("");
  return uuid;
}


export function addDateRange(params, dateRange) {
  var search = params;
  search.beginTime = "";
  search.endTime = "";
  if(null != dateRange && '' != dateRange){
    search.beginTime = this.dateRange[0];
    search.endTime = this.dateRange[1];
  }
  return search;
}
