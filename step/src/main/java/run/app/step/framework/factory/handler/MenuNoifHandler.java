package run.app.step.framework.factory.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import run.app.step.common.constants.Constants;
import run.app.step.common.constants.UserConstants;
import run.app.step.common.exception.BadRequestException;
import run.app.step.common.utils.StringUtils;
import run.app.step.framework.factory.NoIfFactory;
import run.app.step.project.system.entity.SysMenu;
import run.app.step.project.system.service.SysMenuService;

/**
 * @author lingSong
 * @date 2020/9/15 21:51
 */
@Component
public class MenuNoifHandler extends AbstractNoIfHandler {

    @Autowired
    private SysMenuService menuService;


    @Override
    public void insertOrUpdateMenuNoif(SysMenu menu) {
        if(UserConstants.NOT_UNIQUE.equals(menuService.checkMenuNameUnique(menu))){
            throw new BadRequestException("新增菜单" + menu.getMenuName() +"失败， 该菜单名已存在");
        }else if(UserConstants.YES_FRAME == menu.getIsFrame()
                && !StringUtils.startsWithAny(menu.getPath(), Constants.HTTP, Constants.HTTPS)){
            throw new BadRequestException("新增菜单'" + menu.getMenuName() + "'失败，地址必须以http(s)://开头");
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        NoIfFactory.register(Constants.MENU_NO_IF, this);
    }
}
