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

export function addTitle(params, dateTime) {
  var result = params;
  if(null != dateTime && '' != dateTime){
    result.noticeTitle = parseTime(this.dateTime, '{y}-{m}-{d}') + " " + result.noticeTitle;
  }
  return result;
}

// 日期格式
function parseTime(time, pattern) {
  if (arguments.length === 0 || !time) {
    return null
  }
  const format = pattern || '{y}-{m}-{d} {h}:{i}:{s}'
  let date
  if (typeof time === 'object') {
    date = time
  } else {
    if ((typeof time === 'string') && (/^[0-9]+$/.test(time))) {
      time = parseInt(time)
    } else if (typeof time === 'string') {
      time = time.replace(new RegExp(/-/gm), '/');
    }
    if ((typeof time === 'number') && (time.toString().length === 10)) {
      time = time * 1000
    }
    date = new Date(time)
  }
  const formatObj = {
    y: date.getFullYear(),
    m: date.getMonth() + 1,
    d: date.getDate(),
    h: date.getHours(),
    i: date.getMinutes(),
    s: date.getSeconds(),
    a: date.getDay()
  }
  const time_str = format.replace(/{(y|m|d|h|i|s|a)+}/g, (result, key) => {
    let value = formatObj[key]
    // Note: getDay() returns 0 on Sunday
    if (key === 'a') { return ['日', '一', '二', '三', '四', '五', '六'][value] }
    if (result.length > 0 && value < 10) {
      value = '0' + value
    }
    return value || 0
  })
  return time_str
}


