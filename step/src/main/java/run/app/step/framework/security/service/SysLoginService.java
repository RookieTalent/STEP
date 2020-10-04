package run.app.step.framework.security.service;

import cn.hutool.core.lang.Validator;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import run.app.step.common.constants.Constants;
import run.app.step.common.constants.JwtConstants;
import run.app.step.common.constants.UserConstants;
import run.app.step.common.exception.BadRequestException;
import run.app.step.framework.cache.RedisService;
import run.app.step.framework.event.LoginEvent;
import run.app.step.framework.factory.NoIfFactory;
import run.app.step.framework.factory.handler.AbstractNoIfHandler;
import run.app.step.framework.security.LoginUser;
import run.app.step.framework.security.utils.JwtTokenUtil;
import run.app.step.project.system.entity.SysUser;
import run.app.step.project.system.entity.param.LoginParam;
import run.app.step.project.system.entity.vo.TokenVO;
import run.app.step.project.system.service.SysUserService;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author lingSong
 * @date 2020/9/27 20:48
 */
@Component
public class SysLoginService {

    @Autowired
    private SysUserService userService;

    @Autowired
    private RedisService redisService;

    @Autowired
    private ApplicationContext applicationContext;

    public String auth(LoginParam loginParam) {
        Assert.notNull(loginParam, "Login param must not be null");

        //验证码校验
        String verifyKey = Constants.CAPTCHA_CODE_KEY + loginParam.getUuid();
        String captcha= (String) redisService.get(verifyKey);
        redisService.delete(verifyKey);

        String code = loginParam.getCode();
        String username = loginParam.getUsername();

        final SysUser sysUser;

        //通过手机号或者邮箱获取用户信息
        sysUser = Validator.isEmail(username) ?
                userService.findByEmailOfNonNull(username) : userService.findByMobileOfNonNull(username);

        //校验
        AbstractNoIfHandler strategy = NoIfFactory.getInvokeStrategy(Constants.USER_NO_IF);
        Authentication authentication = strategy.authNoif(captcha, code, sysUser, loginParam);

        //记录登录信息
        applicationContext.publishEvent(new LoginEvent(this, sysUser.getNickname(), Constants.LOGIN_SUCCESS, UserConstants.LOGIN_SUCCESS_TIP));

        // 取得登录主体
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();

        return buildAuthToken(loginUser);
    }


    /**
     * 构建token
     * @param sysUser
     * @param loginParam
     * @return
     */
    private String buildAuthToken(@NonNull LoginUser loginUser){
        Assert.notNull(loginUser, "login user must not be null");

        //构建claim
        Map<String, Object> claims = new HashMap<>();
        JwtTokenUtil.setClaims(claims, loginUser);

        //生成token
        String accesToken = JwtTokenUtil.getAccesToken(loginUser.getUser().getId(), claims);

        redisService.set(JwtTokenUtil.getTokenKey(loginUser.getToken()), loginUser, JwtConstants.ACCESS_TOKEN_EXPIRETIME.toMillis(), TimeUnit.MILLISECONDS);
        return accesToken;
    }


    /**
     * TODO 待完成
     */
    public String refreshToken(String refreshToken) {
        // 校验用户数是否已经被加入黑名单 或 refreshtoken已经过期
        if(!JwtTokenUtil.validateToken(refreshToken) || redisService.hasKey(Constants.JWT_REFRESH_TOKEN_BLACKLIST+refreshToken)){
            throw new BadRequestException("token 令牌已过期或在黑名单");
        }

        String userId = JwtTokenUtil.obtainUserId(refreshToken);
        Map<String, Object> claims = null;

        // 用户主动去刷新、更新角色、权限、个人信息等

        return null;
    }



}
