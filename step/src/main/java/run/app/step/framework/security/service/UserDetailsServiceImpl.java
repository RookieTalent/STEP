package run.app.step.framework.security.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import run.app.step.common.constants.UserConstants;
import run.app.step.common.exception.BadRequestException;
import run.app.step.common.utils.StringUtils;
import run.app.step.framework.security.LoginUser;
import run.app.step.project.system.entity.SysUser;
import run.app.step.project.system.service.SysUserService;

import java.util.HashSet;
import java.util.Set;

/**
 * @author lingSong
 * @date 2020/9/27 21:02
 */

@Slf4j
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private SysUserService userService;

    @Autowired
    private SysPermissionService permissionService;

    @Override
    public UserDetails loadUserByUsername(String nickname) throws UsernameNotFoundException {
        SysUser user = userService.selectUserByNickName(nickname);
        if(StringUtils.isNull(user)){
            log.info("登录用户：{} 不存在.", nickname);
            throw new BadRequestException("登录用户：" + nickname + " 不存在");
        }
        else if(UserConstants.DELETED.equals(user.getIsDeleted())){
            log.info("登录用户：{} 已被删除.", nickname);
            throw new BadRequestException("对不起，您的账号：" + nickname + " 已被删除");
        }
        else if(UserConstants.DISABLE.equals(user.getIsDisabled())){
            log.info("登录用户：{} 已被停用.", nickname);
            throw new BadRequestException("对不起，您的账号：" + nickname + " 已停用");
        }
        return createLoginUser(user);
    }


    public UserDetails createLoginUser(SysUser user){
        return new LoginUser(user, permissionService.getMenuPermission(user));
    }
}
