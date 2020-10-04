package run.app.step.framework.factory.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import run.app.step.common.constants.Constants;
import run.app.step.common.constants.UserConstants;
import run.app.step.common.exception.AlreadyExistsException;
import run.app.step.common.exception.BadRequestException;
import run.app.step.framework.event.LoginEvent;
import run.app.step.framework.factory.NoIfFactory;
import run.app.step.project.system.entity.SysUser;
import run.app.step.project.system.entity.param.LoginParam;
import run.app.step.project.system.service.SysUserService;

import javax.annotation.Resource;

/**
 * TODO 存在BUG
 *
 * @author lingSong
 * @date 2020/9/1 20:55
 */
@Component
public class UserNoIfHandler extends AbstractNoIfHandler {

    @Autowired
    private ApplicationContext applicationContext;

    @Resource
    private AuthenticationManager authenticationManager;

    @Autowired
    private SysUserService sysUserService;

    @Override
    public Authentication authNoif(String captcha, String code, SysUser userInfo, LoginParam loginParam) {
        final String nickname = userInfo.getNickname();

        if(captcha == null){
            applicationContext.publishEvent(new LoginEvent(this, nickname, Constants.LOGIN_FAIL, UserConstants.LOGIN_FAIL_CAPTCHA_EXPIRE));
            throw new BadRequestException(UserConstants.LOGIN_FAIL_CAPTCHA_EXPIRE);
        }
        if(!code.equalsIgnoreCase(captcha)){
            applicationContext.publishEvent(new LoginEvent(this, nickname, Constants.LOGIN_FAIL, UserConstants.LOGIN_FAIL_CAPTCHA_ERROR));
            throw new BadRequestException(UserConstants.LOGIN_FAIL_CAPTCHA_ERROR);
        }

        // 用户主体
        Authentication authentication = null;

        try {
            // 该方法会去调用userDetailsServiceImpl.loadUserByUsername
            authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(nickname, loginParam.getPassword()));
        }catch (Exception e){
            if(e instanceof BadCredentialsException){
                applicationContext.publishEvent(new LoginEvent(this, nickname, Constants.LOGIN_FAIL, UserConstants.LOGIN_FAIL_PASSWORD_ERROR));
                throw new BadRequestException(UserConstants.LOGIN_FAIL_PASSWORD_ERROR);
            }else{
                applicationContext.publishEvent(new LoginEvent(this, nickname, Constants.LOGIN_FAIL, e.getMessage()));
                throw new BadRequestException(e.getMessage());
            }
        }

        /*if(!PasswordUtils.matches(userInfo.getSalt(), loginParam.getPassword(), userInfo.getPassword())){
            applicationContext.publishEvent(new LoginEvent(this, nickname, Constants.LOGIN_FAIL, UserConstants.LOGIN_FAIL_PASSWORD_ERROR));
            throw new BadRequestException(UserConstants.LOGIN_FAIL_PASSWORD_ERROR);
        }*/

        return authentication;
    }


    @Override
    public void insertUserNoif(SysUser newUser) {
        if(UserConstants.NOT_UNIQUE.equals(sysUserService.checkNickNameUnique(newUser.getNickname()))){
            throw new AlreadyExistsException("用户名" + newUser.getNickname() + " 已存在");
        }else if(UserConstants.NOT_UNIQUE.equals(sysUserService.checkMobileUnique(newUser.getMobile()))){
            throw new AlreadyExistsException("手机号" + newUser.getMobile() + " 已存在");
        }else if(UserConstants.NOT_UNIQUE.equals(sysUserService.checkEmailUnique(newUser.getEmail()))){
            throw new AlreadyExistsException("邮箱" + newUser.getEmail() + " 已存在");
        }
    }

    @Override
    public void updateUserNoif(SysUser sysUser) {
        if(UserConstants.NOT_UNIQUE.equals(sysUserService.checkMobileUnique(sysUser.getMobile()))){
            throw new AlreadyExistsException("手机号" + sysUser.getMobile() + " 已存在");
        }else if(UserConstants.NOT_UNIQUE.equals(sysUserService.checkEmailUnique(sysUser.getEmail()))){
            throw new AlreadyExistsException("邮箱" + sysUser.getEmail() + " 已存在");
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        NoIfFactory.register(Constants.USER_NO_IF, this);
    }
}
