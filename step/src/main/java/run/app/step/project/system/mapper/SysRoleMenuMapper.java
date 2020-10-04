package run.app.step.project.system.mapper;

import org.springframework.stereotype.Repository;
import run.app.step.project.system.entity.SysRoleMenu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

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

}
