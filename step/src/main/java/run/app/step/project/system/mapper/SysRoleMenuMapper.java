package run.app.step.project.system.mapper;

import org.springframework.stereotype.Repository;
import run.app.step.project.system.entity.SysRoleMenu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.ArrayList;

/**
 * <p>
 * 角色和菜单关联表 Mapper 接口
 * </p>
 *
 * @author lingSong
 * @since 2020-09-29
 */
@Repository
public interface SysRoleMenuMapper extends BaseMapper<SysRoleMenu> {

    /**
     * 批量新增角色菜单信息
     *
     * @param list
     * @return
     */
    int batchRoleMenu(ArrayList<SysRoleMenu> list);

    /**
     * 删除角色与菜单关联
     *
     * @param id
     */
    void deleteRoleMenuByRoleId(Long id);
}
