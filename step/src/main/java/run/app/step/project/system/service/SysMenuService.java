package run.app.step.project.system.service;

import run.app.step.project.system.entity.SysMenu;
import com.baomidou.mybatisplus.extension.service.IService;
import run.app.step.project.system.entity.param.system.menu.MenuQuery;
import run.app.step.project.system.entity.vo.TreeSelect;

import java.util.List;
import java.util.Set;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author lingSong
 * @since 2020-09-13
 */
public interface SysMenuService extends IService<SysMenu> {

    /**
     * 获取菜单列表
     *
     * @param userId
     * @return
     */
    List<SysMenu> selectMenuList(String  userId);


    /**
     * 获取菜单列表
     *
     * @param menuQuery
     * @return
     */
    List<SysMenu> selectMenuList(MenuQuery menuQuery);

    /**
     * 构建前端所需下拉树结构
     *
     * @param menus
     * @return
     */
    List<TreeSelect> buildMenuTreeSelect(List<SysMenu> menus);

    /**
     * 检测菜单名称是否唯一
     *
     * @param menu
     * @return
     */
    String checkMenuNameUnique(SysMenu menu);

    /**
     * 查询一个新的菜单/目录/按钮
     *
     * @param menu
     */
    void insert(SysMenu menu);

    /**
     * 修改目前已有的菜单/目录/按钮
     *
     * @param menu
     */
    void updateMenu(SysMenu menu);

    /**
     * 根据菜单编号获取详细信息
     *
     * @param id
     * @return
     */
    SysMenu selectMenuById(String id);

    /**
     * 根据菜单编号删除菜单信息
     *
     * @param id
     */
    void deleteMenuById(String id);

    /**
     * 查询该菜单编号是否存在子菜单
     *
     * @param id
     * @return
     */
    boolean hasChildByMenuId(String id);

    /**
     * 根据用户ID查询权限
     *
     * @param userId
     * @return
     */
    Set<String> selectMenuPermsByUserId(String userId);

    /**
     * 根据角色ID查询菜单树信息
     *
     * @param id
     * @return
     */
    List<Integer> selectMenuListByRoleId(Long id);
}
