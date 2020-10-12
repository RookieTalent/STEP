package run.app.step.project.system.mapper;

import org.springframework.stereotype.Repository;
import run.app.step.project.system.entity.SysUserRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 用户和角色关联表 Mapper 接口
 * </p>
 *
 * @author lingSong
 * @since 2020-10-06
 */
@Repository
public interface SysUserRoleMapper extends BaseMapper<SysUserRole> {

    /**
     * 插入用户与角色关联表信息
     *
     * @param list
     */
    void batchUserRole(List<SysUserRole> list);

    /**
     * 删除用户与角色关联表信息
     *
     * @param userId
     */
    void deleteUserRoleByUserId(String userId);

    /**
     * 通过角色ID查询角色使用数量
     *
     * @param roleId
     * @return
     */
    int countUserRoleByRoleId(Long roleId);
}
