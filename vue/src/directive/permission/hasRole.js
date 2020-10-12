/**
 * inserted:被绑定元素插入父节点时调用（父节点存在即可调用，不必存在于 document 中）。
 * 角色权限处理
 * */

import store from '@/store';

export default {
  inserted(el, binding, vnode){
    const { value } = binding;
    const super_admin = "admin";
    const roles = store.getters && store.getters.roles;

    if (value && value instanceof Array && value.length > 0) {
      const roleFlag = value;

      const hasRole = roles.some(role => {
        return super_admin === role || roleFlag.includes(role)
      });

      if (!hasRole) {
        el.parentNode && el.parentNode.removeChild(el)
      }
    } else {
      throw new Error(`请设置角色权限标签值"`)
    }

  }
}
