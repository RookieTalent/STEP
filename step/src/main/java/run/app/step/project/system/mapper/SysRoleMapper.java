package run.app.step.project.system.mapper;

import org.springframework.stereotype.Repository;
import run.app.step.project.system.entity.SysRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

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
}
