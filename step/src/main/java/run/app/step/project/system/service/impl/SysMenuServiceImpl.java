package run.app.step.project.system.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import run.app.step.common.constants.Constants;
import run.app.step.common.constants.UserConstants;
import run.app.step.common.exception.AlreadyExistsException;
import run.app.step.common.exception.BadRequestException;
import run.app.step.common.utils.ServiceUtil;
import run.app.step.common.utils.StringUtils;
import run.app.step.framework.factory.NoIfFactory;
import run.app.step.framework.factory.handler.AbstractNoIfHandler;
import run.app.step.framework.factory.handler.MenuNoifHandler;
import run.app.step.project.system.entity.SysMenu;
import run.app.step.project.system.entity.param.system.menu.MenuQuery;
import run.app.step.project.system.entity.vo.TreeSelect;
import run.app.step.project.system.mapper.SysMenuMapper;
import run.app.step.project.system.service.SysMenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author lingSong
 * @since 2020-09-13
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements SysMenuService {

    @Autowired
    private SysMenuMapper menuMapper;

    @Override
    public List<SysMenu> selectMenuList(MenuQuery menuQuery) {
        List<SysMenu> menuList = null;

        //TODO 判断是否是管理员
        menuList = menuMapper.selectMenuList(menuQuery);

        //TODO 不是管理员需要根据用户id去查询对应的可浏览的菜单列表
        return menuList;
    }

    @Override
    public List<TreeSelect> buildMenuTreeSelect(List<SysMenu> menus) {
        List<SysMenu> menuTree = buildMenuTree(menus);
        return menuTree.stream().map(TreeSelect::new).collect(Collectors.toList());
    }

    @Override
    public String checkMenuNameUnique(SysMenu menu) {
        final String parentId = menu.getParentId();
        final String id = menu.getId();

        SysMenu info = menuMapper.checkMenuNameUnique(menu.getMenuName(), parentId);
        if(StringUtils.isNotNull(info) && !info.getId().equals(id)){
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    @Override
    public void insert(SysMenu menu) {
        AbstractNoIfHandler strategy = NoIfFactory.getInvokeStrategy(Constants.MENU_NO_IF);
        strategy.insertOrUpdateMenuNoif(menu);

        ServiceUtil.throwsException(menuMapper.insert(menu), "新增菜单/目录/按钮失败");
    }

    @Override
    public void updateMenu(SysMenu menu) {
        AbstractNoIfHandler strategy = NoIfFactory.getInvokeStrategy(Constants.MENU_NO_IF);
        strategy.insertOrUpdateMenuNoif(menu);

        ServiceUtil.throwsException(menuMapper.updateById(menu), "修改菜单/目录/按钮失败");
    }

    @Override
    public SysMenu selectMenuById(String id) {
        return menuMapper.selectMenuById(id);
    }

    @Override
    public void deleteMenuById(String id) {
        if(hasChildByMenuId(id)){
            throw new AlreadyExistsException("存在子菜单，不允许删除!");
        }
        menuMapper.deleteMenuById(id);
    }

    @Override
    public boolean hasChildByMenuId(String id) {
        int result = menuMapper.hasChildByMenuId(id);
        return result > 0 ? true : false;
    }

    @Override
    public Set<String> selectMenuPermsByUserId(String userId) {
        List<String> perms = menuMapper.selectMenuPermsByUserId(userId);
        Set<String> permsSet = new HashSet<>();
        for (String perm : perms) {
            if(StringUtils.isNotNull(perm)){
                permsSet.addAll(Arrays.asList(perm.trim().split(",")));
            }
        }
        return permsSet;
    }


    /**
     * 构建父子级树
     * @param menus
     * @return
     */
    private List<SysMenu> buildMenuTree(List<SysMenu> menus){
        List<SysMenu> returnList  = new ArrayList<>();

        for (Iterator<SysMenu> iterator = menus.iterator(); iterator.hasNext();){
            SysMenu next = (SysMenu)iterator.next();
            // 根据传入的某个父节点ID 遍历该父节点的所有子节点
            if(next.getParentId().equals("0")){
                recursionFn(menus, next);
                returnList.add(next);
            }
        }

        if(returnList.isEmpty()){
            returnList = menus;
        }
        return returnList;
    }

    private void recursionFn(List<SysMenu> list, SysMenu target){
        List<SysMenu> childList = getChildList(list, target);
        target.setChildren(childList);

        for (SysMenu tChild : childList) {
            if(hasChild(list, tChild)){
                // 判断是否有子节点
                Iterator<SysMenu> it = childList.iterator();
                while (it.hasNext()){
                    SysMenu next = (SysMenu)it.next();
                    recursionFn(list, next);
                }
            }
        }
    }


    /**
     * 得到子节点
     * @param list
     * @param target
     * @return
     */
    private List<SysMenu> getChildList(List<SysMenu> list, SysMenu target){
        List<SysMenu> tempList = new ArrayList<>();
        Iterator<SysMenu> it = list.iterator();
        while (it.hasNext()){
            SysMenu next = (SysMenu)it.next();
            if(next.getParentId().equals(target.getId())){
                tempList.add(next);
            }
        }

        return tempList;
    }

    /**
     * 判断是否有子节点
     * @param list
     * @param target
     * @return
     */
    private boolean hasChild(List<SysMenu> list, SysMenu target){
        return getChildList(list, target).size() > 0 ? true : false;
    }
}
