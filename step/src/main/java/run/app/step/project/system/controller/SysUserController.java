package run.app.step.project.system.controller;


import cn.hutool.http.server.HttpServerRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;
import run.app.step.common.constants.Constants;
import run.app.step.common.enums.code.DBResponseCode;
import run.app.step.common.support.AjaxResult;
import run.app.step.common.utils.poi.ExcelUtil;
import run.app.step.framework.web.controller.BaseController;
import run.app.step.project.system.entity.SysUser;
import run.app.step.project.system.entity.param.system.user.SysUserParam;
import run.app.step.project.system.entity.param.system.user.UserQuery;
import run.app.step.project.system.entity.vo.PageVO;
import run.app.step.project.system.entity.vo.system.user.UserVO;
import run.app.step.project.system.service.SysUserService;

import java.util.List;

/**
 * <p>
 *  user前端控制器
 * </p>
 *
 * @author lingSong
 * @since 2020-08-14
 */
@RestController
@RequestMapping("/system/user")
@Api(tags = "系统模块-用户管理")
@CrossOrigin(origins = {"*"}, allowCredentials = "true")
public class SysUserController extends BaseController {

    @Autowired
    private SysUserService sysUserService;

    /**
     * 获取用户信息列表
     * @param userQuery
     * @return
     */
    @PostMapping("/list")
    @ApiOperation(value = "获取用户列表接口")
    public PageVO list(@Validated @RequestBody UserQuery userQuery){
        startPage(userQuery.getPageNum(), userQuery.getPageSize());
        List<UserVO> list = sysUserService.selectUserList(userQuery);
        return getDataTable(list);
    }

    /**
     * 新增用户
     * @param sysUserParam
     * @return
     */
    @PostMapping
    @ApiOperation(value = "新增单个用户接口")
    public AjaxResult createBy(@Validated @RequestBody SysUserParam sysUserParam){
        // convert to
        SysUser newUser = sysUserParam.convertTo();
        sysUserService.insertUser(newUser);

        return AjaxResult.ok();
    }

    /**
     * 根据用户编号获取sys_user表内的信息接口
     * @param id
     * @return
     */
    @GetMapping(value = "/{id}")
    @ApiOperation(value = "根据用户编号获取sys_user表内的信息接口")
    public AjaxResult obtionInfo(@PathVariable(value = "id")String id){
        UserVO userVO = sysUserService.selectUserById(id);
        //TODO 要绑定角色和职称的  其实可以从另一个接口实现 这里只做返回update处的
        return AjaxResult.ok().data("user", userVO);
    }

    /**
     * 更新用户信息接口
     * @param sysUserParam
     * @return
     */
    @PutMapping
    @ApiOperation(value = "更新用户信息接口")
    public AjaxResult edit(@Validated @RequestBody SysUserParam sysUserParam){
        // convert to
        SysUser sysUser = sysUserParam.convertTo();
        sysUserService.updateUser(sysUser);

        return AjaxResult.ok();
    }

    /**
     * 删除用户信息 可批量
     * @param ids 用户id集合
     * @param code aliyun验证码
     * @param mobile 校验手机号
     * @return
     */
    @DeleteMapping(value = "/{ids}/{code}/{mobile}")
    @ApiOperation(value = "删除用户信息接口")
    public AjaxResult remove(@PathVariable String[] ids, @PathVariable String code, @PathVariable String mobile){
        return sysUserService.deleteUserByIds(ids, code, mobile) > 0 ? AjaxResult.ok() : AjaxResult.error(DBResponseCode.DELETE_ERROR);
    }

    /**
     * 通过excel往库中新增用户集合
     * TODO 设置支持覆盖
     * @param file
     * @return
     * @throws Exception
     */
    @PostMapping("/importData")
    @ApiOperation(value = "通过excel表导入新增用户接口")
    public AjaxResult importData(MultipartFile file, boolean updateSupport) throws Exception{
        ExcelUtil<SysUser> util = new ExcelUtil<>(SysUser.class);
        List<SysUser> userList = util.importExcel(file.getInputStream()); // TODO 用户密码还得设置

        String message = sysUserService.importUser(userList, updateSupport);

        return AjaxResult.ok().setMsg(message);
    }

    @GetMapping("/importTemplate")
    @ApiOperation(value = "下载用户xlsx模板")
    public AjaxResult importTemplate(){
        ExcelUtil<SysUser> util = new ExcelUtil<>(SysUser.class);
        return util.importTemplateExcel("用户数据");
    }

    @GetMapping("/export")
    @ApiOperation(value = "导出数据库至xlsx表")
    public AjaxResult export(UserQuery userQuery){
        List<UserVO> list = sysUserService.selectUserList(userQuery);
        ExcelUtil<UserVO> util = new ExcelUtil<>(UserVO.class);
        return util.exportExcel(list, "用户数据");
    }


    //TODO  这里最好再返回mobile和email比较好
    @GetMapping("/treeselect")
    @ApiOperation(value = "获取用户下拉树菜单")
    public AjaxResult treeselect(){
        List<UserVO> list = sysUserService.selectUserList(new UserQuery());

        return AjaxResult.ok().data("userList", sysUserService.buildUserTreeSelect(list));
    }


    @GetMapping("/logout")
    @ApiOperation(value = "用户登出接口")
    public AjaxResult logout(HttpServerRequest request){
        String access_token = request.getHeader(Constants.ACCESS_TOKEN);
        String refresh_token = request.getHeader(Constants.REFRESH_TOKEN);

        sysUserService.logout(access_token, refresh_token);
        return AjaxResult.ok();
    }

    /**
     * TODO 从token中取值 而不是定值
     * @return
     */
    @GetMapping(value = "/info")
    @ApiOperation(value = "获取用户详细信息接口")
    public AjaxResult info(){

        return AjaxResult.ok().data("roles","[admin]")
                .data("name", "admin")
                .data("avatar", "https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
    }



}

