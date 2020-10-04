package run.app.step.framework.listener;

import eu.bitwalker.useragentutils.UserAgent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import run.app.step.common.constants.Constants;
import run.app.step.common.utils.AddressUtils;
import run.app.step.common.utils.IpUtils;
import run.app.step.common.utils.ServiceUtil;
import run.app.step.common.utils.spring.SpringUtils;
import run.app.step.framework.event.LoginEvent;
import run.app.step.project.system.entity.SysLogininfo;
import run.app.step.project.system.service.SysLogininfoService;

import java.util.Date;

/**
 * TODO 存在的问题： 在insert语句后面 接上thr就造成数据库无法插入 但序号是增加了的 这很奇怪
 *
 *
 * @author lingSong
 * @date 2020/9/26 15:28
 */
@Slf4j
@Component
public class LoginInfoListener implements ApplicationListener<LoginEvent> {

    @Override
    public void onApplicationEvent(LoginEvent loginEvent) {
        SysLogininfo logininfo = new SysLogininfo();

        final UserAgent userAgent = UserAgent.parseUserAgentString(ServiceUtil.getRequest().getHeader("User-Agent"));
        final String ip = IpUtils.getIpAddr(ServiceUtil.getRequest());
        final String address = AddressUtils.getRealAddressByIP(ip);

        StringBuilder info = new StringBuilder();
        info.append(getBlock(ip));
        info.append(getBlock(loginEvent.getNickname()));
        info.append(getBlock(loginEvent.getStatus()));
        info.append(getBlock(loginEvent.getMsg()));
        // 打印
        log.info(info.toString());

        // 获取客户端操作系统
        String os = userAgent.getOperatingSystem().getName();
        // 获取客户端浏览器
        String browser = userAgent.getBrowser().getName();

        // 封装对象
        logininfo.setNickname(loginEvent.getNickname());
        logininfo.setIpaddr(ip);
        logininfo.setLoginLocation(address);
        logininfo.setBrowser(browser);
        logininfo.setOs(os);
        logininfo.setMsg(loginEvent.getMsg());

        //日志状态
        if(Constants.LOGIN_SUCCESS.equals(loginEvent.getStatus())){
            logininfo.setStatus(Constants.SUCCESS);
        }else if(Constants.LOGIN_FAIL.equals(loginEvent.getStatus())){
            logininfo.setStatus(Constants.FAIL);
        }

        //插入数据
        SpringUtils.getBean(SysLogininfoService.class).insertLogininfor(logininfo);
    }


    private String getBlock(Object msg){
        if (msg == null)
        {
            msg = "";
        }
        return "[" + msg.toString() + "]";
    }
}
