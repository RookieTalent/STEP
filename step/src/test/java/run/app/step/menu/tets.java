package run.app.step.menu;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import run.app.step.project.system.entity.SysMenu;
import run.app.step.project.system.mapper.SysMenuMapper;

/**
 * @author lingSong
 * @date 2020/9/14 16:22
 */
@SpringBootTest
public class tets {

    @Autowired
    private SysMenuMapper menuMapper;

    @Test
    public void test1(){
        SysMenu menu = new SysMenu();

        menu.setMenuName("重置密码");
        menu.setParentId("1305423894580047874");
        menu.setOrderNum(7);
        menu.setIsFrame(0);
        menu.setMenuType("F");
        menu.setPerms("system:user:resetPwd");
        menu.setCreateBy("小林");
        menu.setUpdateBy("小林");


        menuMapper.insert(menu);
    }
}
