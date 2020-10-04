package run.app.step.project.system.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import run.app.step.common.constants.Constants;
import run.app.step.common.support.AjaxResult;
import run.app.step.framework.security.utils.JwtTokenUtil;
import run.app.step.framework.web.controller.BaseController;
import run.app.step.project.system.entity.param.system.user.SysUserParam;
import run.app.step.project.system.entity.vo.system.user.UserVO;
import run.app.step.project.system.service.SysUserService;

import javax.servlet.http.HttpServletRequest;

/**
 * @author lingSong
 * @date 2020/9/12 21:50
 */
@RestController
@RequestMapping("/system/user/profile")
@Api(tags = "系统模块-用户个人中心管理")
@CrossOrigin(origins = {"*"}, allowCredentials = "true")
public class SysProfileController extends BaseController {

    @Autowired
    private SysUserService userService;

    @GetMapping
    @ApiOperation(value = "查询个人信息")
    public AjaxResult profile(HttpServletRequest request){
        String access_token = request.getHeader(Constants.ACCESS_TOKEN);
        String userId = JwtTokenUtil.obtainUserId(access_token);
        UserVO user = userService.selectUserById(userId);

        //TODO 角色 权限 查询
        return AjaxResult.ok().data("userInfo", user);
    }

    @PutMapping
    @ApiOperation(value = "修改个人信息")
    public AjaxResult updateProfile(@RequestBody SysUserParam userParam){


        return AjaxResult.ok();
    }



}
