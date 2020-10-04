package run.app.step.project.monitor.entity;

import lombok.Data;
import lombok.ToString;

import java.util.Date;

/**
 * 当前在线会话
 *
 * @author lingSong
 * @date 2020/9/27 10:29
 */
@Data
@ToString
public class SysUserOnline {

    /**
     * 登录名称
     */
    private String nickname;

    /**
     * 部门名称
     */
    private String collegeName;

    /**
     * 登录ip地址
     */
    private String ipaddr;

    /**
     * 浏览器
     */
    private String browser;

    /**
     * 操作系统
     */
    private String os;

    /**
     * 登录时间
     */
    private Date loginTime;


}
