package run.app.step.common.constants;

/**
 * @author lingSong
 * @date 2020/8/26 15:58
 */
public class UserConstants {

    /** 部门正常状态 */
    public static final String COLLEGE_NORMAL = "1";

    /** 校验返回结果码*/
    public final static String UNIQUE = "0";
    public final static String NOT_UNIQUE = "1";

    /**
     * 删除标志
     */
    public final static Integer DELETED = 0;

    /**
     * 禁用标志
     */
    public final static Integer DISABLE = 0;

    /** 是否菜单外链（是） */
    public static final Integer YES_FRAME = 1;

    /**
     * 验证码错误
     */
    public static final String LOGIN_FAIL_CAPTCHA_ERROR = "验证码错误";

    /**
     * 验证码过期
     */
    public static final String LOGIN_FAIL_CAPTCHA_EXPIRE = "验证码过期";

    /**
     * 密码错误
     */
    public static final String LOGIN_FAIL_PASSWORD_ERROR = "密码错误";

    /**
     * 用户锁定
     */
    public static final String LOGIN_FAIL_IS_DISABLE = "用户锁定";

    /**
     * 用户不存在
     */
    public static final String LOGIN_FAIL_IS_DELETE = "用户不存在";

    /**
     * 登录成功
     */
    public static final String LOGIN_SUCCESS_TIP = "登陆成功";
}
