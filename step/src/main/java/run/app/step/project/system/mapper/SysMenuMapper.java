package run.app.step.project.system.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import run.app.step.project.system.entity.SysMenu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import run.app.step.project.system.entity.param.system.menu.MenuQuery;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author lingSong
 * @since 2020-09-13
 */
@Repository
public interface SysMenuMapper extends BaseMapper<SysMenu> {

    /**
     * 管理员： 查询系统菜单列表
     * @param menuQuery
     * @return
     */
    List<SysMenu> selectMenuList(MenuQuery menuQuery);

    /**
     * 检测菜单/目录/按钮名称是否唯一
     *
     * @param menuName
     * @param id
     * @return
     */
    SysMenu checkMenuNameUnique(@Param("menuName") String menuName, @Param("id") String id);

    /**
     * 根据菜单编号查询菜单详细信息
     *
     * @param id
     * @return
     */
    SysMenu selectMenuById(String id);

    /**
     * 根据菜单编号查询是否存在子节点
     *
     * @param id
     * @return
     */
    int hasChildByMenuId(String id);

    /**
     * 删除菜单信息
     *
     * @param id
     */
    void deleteMenuById(String id);

    /**
     * 根据用户ID查询权限
     *
     * @param userId
     * @return
     */
    List<String> selectMenuPermsByUserId(String userId);
}
