package run.app.step.project.system.controller;


import cn.hutool.http.server.HttpServerRequest;
import com.sun.org.apache.regexp.internal.RE;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import run.app.step.common.constants.Constants;
import run.app.step.common.enums.code.BusinessType;
import run.app.step.common.support.AjaxResult;
import run.app.step.common.utils.ServiceUtil;
import run.app.step.framework.aspectj.lang.annotation.AutoLog;
import run.app.step.framework.security.utils.JwtTokenUtil;
import run.app.step.framework.web.controller.BaseController;
import run.app.step.project.system.entity.SysMenu;
import run.app.step.project.system.entity.param.system.menu.MenuQuery;
import run.app.step.project.system.service.SysMenuService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author lingSong
 * @since 2020-09-13
 */
@RestController
@RequestMapping("/system/menu")
@Api(tags = "系统模块-菜单管理")
@CrossOrigin(origins = {"*"}, allowCredentials = "true")
public class SysMenuController extends BaseController {

    @Autowired
    private SysMenuService menuService;

    @PostMapping("/list")
    @ApiOperation(value = "查询菜单列表")
    @AutoLog(title = "系统模块-菜单模块", action = "菜单列表", businessType = BusinessType.LIST)
    public AjaxResult list(@RequestBody(required = false) MenuQuery menuQuery){
        //TODO 根据认证主体获取用户id
        List<SysMenu> menus = menuService.selectMenuList(menuQuery);
        return AjaxResult.ok().data("menus", menus);
    }


    @GetMapping("/treeselect")
    @ApiOperation(value = "构建菜单选项树")
    @AutoLog(title = "系统模块-菜单模块", action = "选项树", businessType = BusinessType.OTHER)
    public AjaxResult treeselect(){
        //TODO 根据认证主体获取用户id
        List<SysMenu> menus = menuService.selectMenuList(new MenuQuery());
        return AjaxResult.ok().data("menuTree", menuService.buildMenuTreeSelect(menus));
    }

    @PostMapping
    @ApiOperation(value = "新增菜单/目录/按钮")
    @PreAuthorize("@ss.hasPermi('system:menu:add')")
    @AutoLog(title = "系统模块-菜单模块", action = "新增菜单", businessType = BusinessType.INSERT)
    public AjaxResult insert(@RequestBody SysMenu menu, HttpServletRequest request){
        //TODO 后期转为认证获取
        String accessToken = request.getHeader(Constants.ACCESS_TOKEN);
        String nickname = JwtTokenUtil.obtainNickName(accessToken);

        menu.setCreateBy(nickname);
        menuService.insert(menu);
        return AjaxResult.ok();
    }


    @PutMapping
    @ApiOperation(value = "修改菜单信息")
    @PreAuthorize("@ss.hasPermi('system:menu:edit')")
    @AutoLog(title = "系统模块-菜单模块", action = "修改菜单", businessType = BusinessType.UPDATE)
    public AjaxResult edit(@RequestBody SysMenu menu, HttpServletRequest request){
        //TODO 后期转为认证获取
        String accessToken = request.getHeader(Constants.ACCESS_TOKEN);
        String nickname = JwtTokenUtil.obtainNickName(accessToken);

        menu.setUpdateBy(nickname);
        menuService.updateMenu(menu);
        return AjaxResult.ok();
    }

    @GetMapping(value = "/{id}")
    @ApiOperation(value = "根据菜单编号获取详细信息")
    @PreAuthorize("@ss.hasPermi('system:menu:query')")
    @AutoLog(title = "系统模块-菜单模块", action = "单体查询", businessType = BusinessType.OTHER)
    public AjaxResult getInfo(@PathVariable(value = "id")String id){
        return AjaxResult.ok().data("menu", menuService.selectMenuById(id));
    }


    @DeleteMapping(value = "/{id}")
    @ApiOperation(value = "根据菜单id删除该菜单信息")
    @PreAuthorize("@ss.hasPermi('system:menu:remove')")
    @AutoLog(title = "系统模块-菜单模块", action = "删除菜单", businessType = BusinessType.DELETE)
    public AjaxResult remove(@PathVariable("id")String id){
        menuService.deleteMenuById(id);
        return AjaxResult.ok();
    }

    @GetMapping(value = "/roleMenuTreeselect/{id}")
    @ApiOperation(value = "加载对应角色菜单列表树")
    @AutoLog(title = "系统模块-菜单模块", action = "列表树", businessType = BusinessType.OTHER)
    public AjaxResult roleMenuTreeselect(@PathVariable("id")Long id){
        String userId = JwtTokenUtil.obtainUserId(ServiceUtil.getRequest().getHeader(Constants.ACCESS_TOKEN));
        List<SysMenu> menus = menuService.selectMenuList(userId);
        System.out.println("id = " + id);
        List<Integer> list = menuService.selectMenuListByRoleId(id);
        return AjaxResult.ok().data("menus", menus)
                .data("checkedKeys", list);
    }


}

