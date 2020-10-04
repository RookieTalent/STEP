package run.app.step.project.system.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import run.app.step.common.support.AjaxResult;
import run.app.step.common.utils.ServiceUtil;
import run.app.step.framework.security.LoginUser;
import run.app.step.project.system.entity.SysRole;
import run.app.step.project.system.service.SysRoleService;

/**
 * <p>
 * 角色信息表 前端控制器
 * </p>
 *
 * @author lingSong
 * @since 2020-09-29
 */
@RestController
@RequestMapping("/system/role")
@Api(tags = "系统模块-角色管理")
@CrossOrigin(origins = {"*"}, allowCredentials = "true")
public class SysRoleController {

    @Autowired
    private SysRoleService roleService;

    @GetMapping
    @ApiOperation(value = "角色新增")
    @PreAuthorize("@ss.hasPermi('system:role:add')")
    public AjaxResult insert(){
        Authentication authentication = ServiceUtil.getAuthentication();
        System.out.println("authentication = " + authentication);

        String nickName = ServiceUtil.getNickName();
        System.out.println("nickName = " + nickName);

        return AjaxResult.ok();
    }

}

