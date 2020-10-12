package run.app.step.project.monitor.service;

import run.app.step.framework.security.LoginUser;
import run.app.step.project.monitor.entity.SysUserOnline;

/**
 * @author lingSong
 * @date 2020/9/27 10:36
 */
public interface SysUserOnlineService {

    /**
     * 通过登录地址/用户名称查询信息
     *
     * @param ipaddr
     * @param nickName
     * @param user
     */
    SysUserOnline selectOnlineByInfo(String ipaddr, String nickName, LoginUser user);

    /**
     * 通过登录地址
     *
     * @param ipaddr
     * @param user
     * @return
     */
    SysUserOnline selectOnlineByIpaddr(String ipaddr, LoginUser user);

    /**
     * 通过用户名称查询信息
     *
     * @param nickName
     * @param user
     * @return
     */
    SysUserOnline selectOnlineByNickName(String nickName, LoginUser user);

    /**
     * convert to
     *
     * @param user
     * @return
     */
    SysUserOnline loginUserToUserOnline(LoginUser user);
}
