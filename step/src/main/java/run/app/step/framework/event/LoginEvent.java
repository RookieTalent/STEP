package run.app.step.framework.event;

import org.springframework.context.ApplicationEvent;
import run.app.step.project.system.entity.SysLogininfo;

/**
 * @author lingSong
 * @date 2020/9/26 15:12
 */
public class LoginEvent extends ApplicationEvent{

    private final String nickname;

    private final String status;

    private final String msg;


    public LoginEvent(Object source, String nickname, String status, String msg) {
        super(source);
        this.nickname = nickname;
        this.status = status;
        this.msg = msg;
    }

    public String getNickname() {
        return nickname;
    }

    public String getStatus() {
        return status;
    }

    public String getMsg() {
        return msg;
    }
}
