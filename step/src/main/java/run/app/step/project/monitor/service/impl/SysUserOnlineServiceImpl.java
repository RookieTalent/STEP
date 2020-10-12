package run.app.step.project.monitor.service.impl;

import org.springframework.stereotype.Service;
import run.app.step.common.utils.StringUtils;
import run.app.step.framework.security.LoginUser;
import run.app.step.project.monitor.entity.SysUserOnline;
import run.app.step.project.monitor.service.SysUserOnlineService;

/**
 * @author lingSong
 * @date 2020/9/27 10:36
 */
@Service
public class SysUserOnlineServiceImpl implements SysUserOnlineService {

    @Override
    public SysUserOnline selectOnlineByInfo(String ipaddr, String nickName, LoginUser user) {
        if (StringUtils.equals(ipaddr, user.getIpaddr()) && StringUtils.equals(nickName, user.getUsername())){
            return loginUserToUserOnline(user);
        }

        return null;
    }

    @Override
    public SysUserOnline selectOnlineByIpaddr(String ipaddr, LoginUser user) {
        if (StringUtils.equals(ipaddr, user.getIpaddr())){
            return loginUserToUserOnline(user);
        }

        return null;
    }

    @Override
    public SysUserOnline selectOnlineByNickName(String nickName, LoginUser user) {
        if (StringUtils.equals(nickName, user.getUsername())){
            return loginUserToUserOnline(user);
        }

        return null;
    }

    public SysUserOnline loginUserToUserOnline(LoginUser user) {
        if(StringUtils.isNull(user) && StringUtils.isNull(user.getUser())){
            return null;
        }

        SysUserOnline sysUserOnline = new SysUserOnline();
        sysUserOnline.setTokenId(user.getToken());
        sysUserOnline.setNickname(user.getUsername());
        sysUserOnline.setIpaddr(user.getIpaddr());
        sysUserOnline.setLoginLocation(user.getLoginLocation());
        sysUserOnline.setBrowser(user.getBrowser());
        sysUserOnline.setOs(user.getOs());
        sysUserOnline.setLoginTime(user.getLoginTime());
        if(StringUtils.isNotNull(user.getUser().getCollege())){
            sysUserOnline.setCollegeName(user.getUser().getCollege().getCollegeName());
        }

        return sysUserOnline;
    }
}
