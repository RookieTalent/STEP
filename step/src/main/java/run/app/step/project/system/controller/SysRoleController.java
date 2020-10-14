package run.app.step.project.system.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import run.app.step.common.enums.code.BusinessType;
import run.app.step.common.support.AjaxResult;
import run.app.step.common.utils.ServiceUtil;
import run.app.step.framework.aspectj.lang.annotation.AutoLog;
import run.app.step.framework.security.LoginUser;
import run.app.step.framework.web.controller.BaseController;
import run.app.step.project.system.entity.SysRole;
import run.app.step.project.system.entity.param.system.role.RoleQuery;
import run.app.step.project.system.entity.vo.PageVO;
import run.app.step.project.system.service.SysRoleService;

import java.util.List;

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
public class SysRoleController extends BaseController {

    @Autowired
    private SysRoleService roleService;

    @PostMapping("/list")
    @ApiOperation(value = "获取角色分页列表")
    @AutoLog(title = "系统模块-角色管理", action = "角色列表", businessType = BusinessType.LIST)
    public PageVO list(@RequestBody RoleQuery roleQuery){
        startPage(roleQuery.getPageNum(), roleQuery.getPageSize());
        List<SysRole> list = roleService.selectRoleList(roleQuery);
        return getDataTable(list);
    }

    @PostMapping
    @ApiOperation(value = "角色新增")
    @PreAuthorize("@ss.hasPermi('system:role:add')")
    @AutoLog(title = "系统模块-角色管理", action = "新增角色", businessType = BusinessType.INSERT)
    public AjaxResult insert(@RequestBody SysRole role){
        role.setCreateBy(ServiceUtil.getNickName());
        roleService.insertRole(role);
        return AjaxResult.ok();
    }

    @GetMapping(value = "/{id}")
    @ApiOperation(value = "根据id查询角色的信息")
    @PreAuthorize("@ss.hasPermi('system:role:query')")
    @AutoLog(title = "系统模块-角色管理", action = "单体查询", businessType = BusinessType.OTHER)
    public AjaxResult getInfo(@PathVariable Long id){
        return AjaxResult.ok().data("role", roleService.selectRoleById(id));
    }

    @PutMapping("/dataScope")
    @ApiOperation(value = "角色数据权限范围")
    @PreAuthorize("@ss.hasPermi('system:role:edit')")
    @AutoLog(title = "系统模块-角色管理", action = "权限范围", businessType = BusinessType.UPDATE)
    public AjaxResult dataScope(@RequestBody SysRole role){
        roleService.authDataScope(role);
        return AjaxResult.ok();
    }

    @PutMapping
    @ApiOperation(value = "修改角色信息")
    @PreAuthorize("@ss.hasPermi('system:role:edit')")
    @AutoLog(title = "系统模块-角色管理", action = "更新角色", businessType = BusinessType.UPDATE)
    public AjaxResult edit(@RequestBody SysRole role){
        role.setUpdateBy(ServiceUtil.getNickName());
        roleService.updateRole(role);
        return AjaxResult.ok();
    }

    @DeleteMapping("/{roleIds}")
    @ApiOperation(value = "删除角色信息")
    @PreAuthorize("@ss.hasPermi('system:role:remove')")
    @AutoLog(title = "系统模块-角色管理", action = "删除角色", businessType = BusinessType.DELETE)
    public AjaxResult remove(@PathVariable Long[] roleIds){
        roleService.deleteRoleByIds(roleIds);
        return AjaxResult.ok();
    }
}

