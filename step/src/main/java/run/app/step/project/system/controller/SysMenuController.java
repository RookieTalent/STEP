package run.app.step.project.system.controller;


import cn.hutool.http.server.HttpServerRequest;
import com.sun.org.apache.regexp.internal.RE;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import run.app.step.common.constants.Constants;
import run.app.step.common.support.AjaxResult;
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

    @ApiOperation(value = "查询菜单列表")
    @PostMapping("/list")
    public AjaxResult list(@RequestBody(required = false) MenuQuery menuQuery){
        //TODO 根据认证主体获取用户id
        List<SysMenu> menus = menuService.selectMenuList(menuQuery);
        return AjaxResult.ok().data("menus", menus);
    }


    @GetMapping("/treeselect")
    @ApiOperation(value = "构建菜单选项树")
    public AjaxResult treeselect(){
        //TODO 根据认证主体获取用户id
        List<SysMenu> menus = menuService.selectMenuList(new MenuQuery());
        return AjaxResult.ok().data("menuTree", menuService.buildMenuTreeSelect(menus));
    }

    @PostMapping
    @ApiOperation(value = "新增菜单/目录/按钮")
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
    public AjaxResult getInfo(@PathVariable(value = "id")String id){
        return AjaxResult.ok().data("menu", menuService.selectMenuById(id));
    }


    @DeleteMapping(value = "/{id}")
    @ApiOperation(value = "根据菜单id删除该菜单信息")
    public AjaxResult remove(@PathVariable("id")String id){
        menuService.deleteMenuById(id);
        return AjaxResult.ok();
    }
}

