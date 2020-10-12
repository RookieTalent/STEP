package run.app.step.project.system.service;

import run.app.step.project.system.entity.SysRole;
import com.baomidou.mybatisplus.extension.service.IService;
import run.app.step.project.system.entity.param.system.role.RoleQuery;

import java.util.List;
import java.util.Set;

/**
 * <p>
 * 角色信息表 服务类
 * </p>
 *
 * @author lingSong
 * @since 2020-09-29
 */
public interface SysRoleService extends IService<SysRole> {

    /**
     * 校验角色名称是否唯一
     *
     * @param role
     * @return
     */
    String checkRoleNameUnique(SysRole role);


    /**
     * 校验角色权限是否唯一
     *
     * @param role
     * @return
     */
    String checkRoleKeyUnique(SysRole role);


    /**
     * 根据用户id去查询角色
     *
     * @param userId
     * @return
     */
    Set<String> selectRolePermissionByUserId(String userId);

    /**
     * 新增角色
     *
     * @param role
     */
    void insertRole(SysRole role);

    /**
     * 查询所有角色
     *
     * @return
     */
    List<SysRole> selectRoleAll();

    /**
     * 根据用户id去查询拥有角色信息
     *
     * @param id
     * @return
     */
    List<Integer> selectRoleListByUserId(String id);

    /**
     * 根据条件分页查询角色数据
     *
     * @param roleQuery
     * @return
     */
    List<SysRole> selectRoleList(RoleQuery roleQuery);

    /**
     * 根据id查询某角色信息
     *
     * @param id
     * @return
     */
    SysRole selectRoleById(Long id);

    /**
     * 修改数据权限信息
     *
     * @param role
     */
    void authDataScope(SysRole role);

    /**
     * 修改角色信息
     *
     * @param role
     */
    void updateRole(SysRole role);

    /**
     * 批量删除角色信息
     *
     * @param roleIds
     */
    void deleteRoleByIds(Long[] roleIds);

    /**
     * 通过角色ID查询角色使用数量
     *
     * @param roleId
     * @return
     */
    int countUserRoleByRoleId(Long roleId);
}
