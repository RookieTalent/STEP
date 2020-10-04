package run.app.step.framework.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import run.app.step.project.system.entity.SysUser;
import run.app.step.project.system.service.SysMenuService;
import run.app.step.project.system.service.SysRoleService;

import java.util.HashSet;
import java.util.Set;

/**
 * @author lingSong
 * @date 2020/9/29 15:42
 */
@Component
public class SysPermissionService {

    @Autowired
    private SysRoleService roleService;

    @Autowired
    private SysMenuService menuService;

    /**
     * 获取角色数据权限
     *
     * @param user
     * @return
     */
    public Set<String> getRolePermssion(SysUser user){
        Set<String> roles = new HashSet<String>();
        //TODO  暂定
        if("小林".equals(user.getNickname())){
            roles.add("admin");
        }else{
            roles.addAll(roleService.selectRolePermissionByUserId(user.getId()));
        }
        return roles;
    }


    public Set<String> getMenuPermission(SysUser user){
        Set<String> perms = new HashSet<>();
        //TODO 暂定
        if("小林".equals(user.getNickname())){
            perms.add("*:*:*");
        }else{
            perms.addAll(menuService.selectMenuPermsByUserId(user.getId()));
        }
        return perms;
    }

}
