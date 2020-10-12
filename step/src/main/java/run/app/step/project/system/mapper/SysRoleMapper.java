package run.app.step.project.system.mapper;

import org.springframework.stereotype.Repository;
import run.app.step.project.system.entity.SysRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import run.app.step.project.system.entity.param.system.role.RoleQuery;

import java.util.List;

/**
 * <p>
 * 角色信息表 Mapper 接口
 * </p>
 *
 * @author lingSong
 * @since 2020-09-29
 */
@Repository
public interface SysRoleMapper extends BaseMapper<SysRole> {

    /**
     * 校验角色名称是否唯一
     *
     * @param roleName
     * @return
     */
    SysRole checkRoleNameUnique(String roleName);

    /**
     * 校验角色权限是否唯一
     *
     * @param roleKey
     * @return
     */
    SysRole checkRoleKeyUnique(String roleKey);

    /**
     * 根据用户ID查询权限
     *
     * @param userId
     * @return
     */
    List<SysRole> selectRolePermissionByUserId(String userId);

    /**
     * 查询所有角色信息
     *
     * @return
     */
    List<SysRole> selectRoleAll();

    /**
     * 根据id查询用户角色
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
     * 根据id查询角色信息
     *
     * @param id
     * @return
     */
    SysRole selectRoleById(Long id);

    /**
     * 批量删除角色信息
     *
     * @param roleIds
     */
    void deleteRoleByIds(Long[] roleIds);
}
