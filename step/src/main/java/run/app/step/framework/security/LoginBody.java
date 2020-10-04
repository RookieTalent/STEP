package run.app.step.framework.security;

import lombok.Data;
import lombok.ToString;

/**
 * @author lingSong
 * @date 2020/9/27 20:03
 */
@Data
@ToString
public class LoginBody {

    /**
     * 用户名
     */
    private String nickname;

    /**
     * 用户密码
     */
    private String password;

    /**
     * 验证码
     */
    private String code;

    /**
     * 唯一标识
     */
    private String uuid = "";

}
