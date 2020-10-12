package run.app.step.project.system.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import run.app.step.common.constants.UserConstants;
import run.app.step.common.exception.BadRequestException;
import run.app.step.common.exception.DBException;
import run.app.step.common.utils.ServiceUtil;
import run.app.step.common.utils.StringUtils;
import run.app.step.project.system.entity.SysRole;
import run.app.step.project.system.entity.SysRoleCollege;
import run.app.step.project.system.entity.SysRoleMenu;
import run.app.step.project.system.entity.param.system.role.RoleQuery;
import run.app.step.project.system.mapper.SysRoleCollegeMapper;
import run.app.step.project.system.mapper.SysRoleMapper;
import run.app.step.project.system.mapper.SysRoleMenuMapper;
import run.app.step.project.system.mapper.SysUserRoleMapper;
import run.app.step.project.system.service.SysRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * <p>
 * 角色信息表 服务实现类
 * </p>
 *
 * @author lingSong
 * @since 2020-09-29
 */
@Service
@Transactional(rollbackFor = DBException.class)
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {

    @Autowired
    private SysRoleMapper roleMapper;

    @Autowired
    private SysUserRoleMapper userRoleMapper;

    @Autowired
    private SysRoleMenuMapper roleMenuMapper;

    @Autowired
    private SysRoleCollegeMapper roleCollegeMapper;

    @Override
    public String checkRoleNameUnique(SysRole role) {
        SysRole info = roleMapper.checkRoleNameUnique(role.getRoleName());
        if(StringUtils.isNotNull(info)){
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    @Override
    public String checkRoleKeyUnique(SysRole role) {
        SysRole info = roleMapper.checkRoleKeyUnique(role.getRoleKey());
        if(StringUtils.isNotNull(info)){
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    @Override
    public Set<String> selectRolePermissionByUserId(String userId) {
        List<SysRole> perms = roleMapper.selectRolePermissionByUserId(userId);
        Set<String> permsSet = new HashSet<>();
        for (SysRole perm : perms) {
            if(StringUtils.isNotNull(perm)){
                permsSet.addAll(Arrays.asList(perm.getRoleKey().trim().split(",")));
            }
        }
        return permsSet;
    }

    @Override
    public void insertRole(SysRole role) {
        // 校验
        if(UserConstants.NOT_UNIQUE.equals(checkRoleNameUnique(role))){
            throw new BadRequestException("新增角色'" + role.getRoleName() + "'失败，角色名称已存在");
        }else if(UserConstants.NOT_UNIQUE.equals(checkRoleKeyUnique(role))){
            throw new BadRequestException("新增角色'" + role.getRoleName() + "'失败，角色权限已存在");
        }
        // 新增角色信息
        ServiceUtil.throwsException(roleMapper.insert(role), "插入角色信息失败，请稍后操作");
        // 绑定角色菜单
        insertRoleMenu(role);
    }

    @Override
    public List<SysRole> selectRoleAll() {
        return roleMapper.selectRoleAll();
    }

    @Override
    public List<Integer> selectRoleListByUserId(String id) {
        return roleMapper.selectRoleListByUserId(id);
    }

    @Override
    public List<SysRole> selectRoleList(RoleQuery roleQuery) {
        return roleMapper.selectRoleList(roleQuery);
    }

    @Override
    public SysRole selectRoleById(Long id) {
        return roleMapper.selectRoleById(id);
    }

    @Override
    public void authDataScope(SysRole role) {
        // 修改角色信息
        roleMapper.updateById(role);
        // 删除角色与学院关联
        roleCollegeMapper.deleteRoleCollegeByRoleId(role.getId());
        //新增角色和学院信息(数据权限)
        insertRoleCollege(role);
    }

    @Override
    public void updateRole(SysRole role) {
        // 校验
        if(UserConstants.NOT_UNIQUE.equals(checkRoleNameUnique(role))){
            throw new BadRequestException("新增角色'" + role.getRoleName() + "'失败，角色名称已存在");
        }else if(UserConstants.NOT_UNIQUE.equals(checkRoleKeyUnique(role))){
            throw new BadRequestException("新增角色'" + role.getRoleName() + "'失败，角色权限已存在");
        }
        // 修改角色信息
        roleMapper.updateById(role);
        // 删除角色和菜单关联表
        roleMenuMapper.deleteRoleMenuByRoleId(role.getId());
        // 重新关联
        insertRoleMenu(role);
    }

    @Override
    public void deleteRoleByIds(Long[] roleIds) {
        for (Long roleId : roleIds) {
            SysRole role = selectRoleById(roleId);
            if(countUserRoleByRoleId(roleId) > 0){
                throw new BadRequestException("角色已分配，不能删除");
            }
        }
        roleMapper.deleteRoleByIds(roleIds);
    }

    @Override
    public int countUserRoleByRoleId(Long roleId) {
        return userRoleMapper.countUserRoleByRoleId(roleId);
    }


    /**
     * 新增角色学院信息
     *
     * @param role
     */
    private void insertRoleCollege(SysRole role) {
        int rows = 1;
        // 新增角色和学院管理
        ArrayList<SysRoleCollege> list = new ArrayList<>();
        for (String collegeId : role.getCollegeIds()) {
            SysRoleCollege rc = new SysRoleCollege();
            rc.setRoleId(role.getId());
            rc.setCollegeId(collegeId);
            list.add(rc);
        }
        if(list.size() > 0){
            rows = roleCollegeMapper.batchRoleCollege(list);
        }
        ServiceUtil.throwsException(rows, "批量插入角色与学院关联信息失败");
    }

    /**
     * 新增角色菜单信息
     *
     * @param role
     */
    public void insertRoleMenu(SysRole role){
        int rows = 1;
        // 新增用户与角色管理
        ArrayList<SysRoleMenu> list = new ArrayList<>();
        for (String menuId : role.getMenuIds()) {
            SysRoleMenu rm = new SysRoleMenu();
            rm.setRoleId(role.getId());
            rm.setMenuId(menuId);
            list.add(rm);
        }
        if(list.size() > 0){
            rows = roleMenuMapper.batchRoleMenu(list);
        }
        ServiceUtil.throwsException(rows, "设置角色与菜单关联失败");
    }
}
