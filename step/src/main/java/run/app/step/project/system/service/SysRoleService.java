package run.app.step.project.system.service;

import run.app.step.project.system.entity.SysRole;
import com.baomidou.mybatisplus.extension.service.IService;

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
}
