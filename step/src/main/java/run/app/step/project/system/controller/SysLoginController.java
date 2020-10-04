package run.app.step.project.system.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import run.app.step.common.constants.Constants;
import run.app.step.common.support.AjaxResult;
import run.app.step.framework.security.service.SysLoginService;
import run.app.step.framework.web.controller.BaseController;
import run.app.step.project.system.entity.param.LoginParam;
import run.app.step.project.system.entity.vo.TokenVO;
import run.app.step.project.system.service.SysUserService;

/**
 * @author lingSong
 * @date 2020/8/15 15:40
 */
@RestController
@Api(tags = "系统模块-用户登录")
@CrossOrigin(origins = {"*"}, allowCredentials = "true")
public class SysLoginController extends BaseController {

    @Autowired
    private SysLoginService loginService;

    @PostMapping(value = "/login")
    @ApiOperation(value = "用户登录接口")
    public AjaxResult login(@RequestBody LoginParam loginParam){
        return AjaxResult.ok().data(Constants.ACCESS_TOKEN, loginService.auth(loginParam));
    }


}
