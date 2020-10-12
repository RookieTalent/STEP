package run.app.step.project.common;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import run.app.step.common.constants.Constants;
import run.app.step.common.enums.code.UserResponseCode;
import run.app.step.common.support.AjaxResult;
import run.app.step.common.utils.ServiceUtil;
import run.app.step.common.utils.VerifyCodeUtils;
import run.app.step.framework.security.utils.JwtTokenUtil;
import run.app.step.framework.web.service.SmsService;
import run.app.step.project.system.service.SysUserService;

import javax.servlet.http.HttpServletRequest;

/**
 * aliyun验证码
 *
 * @author lingSong
 * @date 2020/8/28 14:31
 */
@RestController
@Api(tags = "通用模块-用户删除aliyun验证码模块")
@CrossOrigin(origins = {"*"}, allowCredentials = "true")
public class CodeController {

    @Autowired
    private SmsService smsService;

    @Autowired
    private SysUserService userService;

    /**
     * 获取短信验证码
     * @param request
     * @return
     */
    @GetMapping("/system/user/captcha")
    @ApiOperation(value = "获取短信验证码")
    public AjaxResult obtionCaptcha(){
        String authorization = ServiceUtil.getRequest().getHeader(Constants.ACCESS_TOKEN);

        //生成随机值
        String code = VerifyCodeUtils.generateVerifyCode(4, VerifyCodeUtils.NUMBER_VERIFY_CODES);
        String mobile = userService.selectUserById(JwtTokenUtil.obtainUserId(authorization)).getMobile();

        boolean result = smsService.send(code, mobile);
        return result ? AjaxResult.ok().data("mobile", mobile) : AjaxResult.error(UserResponseCode.CAPTCHA_ERROR);
    }


}
