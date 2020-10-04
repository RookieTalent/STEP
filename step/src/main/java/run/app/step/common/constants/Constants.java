package run.app.step.common.constants;

/**
 * @author lingSong
 * @date 2020/8/15 18:23
 */
public class Constants {

    /**
     * GBK 字符集
     */
    public static final String GBK = "GBK";

    /**
     * http请求
     */
    public static final String HTTP = "http://";

    /**
     * https请求
     */
    public static final String HTTPS = "https://";

    /**
     * 用户名称key
     */
    public static final String JWT_Nick_NAME="jwt_nickname_key:";

    /**
     * 令牌
     */
    public static final String ACCESS_TOKEN = "Authorization";

    /**
     * 登录用户 redis key
     */
    public static final String LOGIN_TOKEN_KEY = "login_tokens:";

    /**
     * 令牌前缀
     */
    public static final String LOGIN_USER_KEY = "login_user_key";

    /**
     * TODO 刷新令牌
     */
    public static final String REFRESH_TOKEN = "refresh_token";


    /**
     * access_token 自动退出后加入黑名单key
     */
    public static final String JWT_ACCESS_TOKEN_BLACKLIST = "jwt-access-token-blacklist:";

    /**
     * refresh_token 自动退出后加入黑名单key4
     */
    public static final String JWT_REFRESH_TOKEN_BLACKLIST = "jwt-refresh-token-blacklist:";

    /**
     * 图片验证码 redis key
     */
    public static final String CAPTCHA_CODE_KEY = "captcha_codes:";

    /**
     * 图片验证码有效期（分钟）
     */
    public static final Integer CAPTCHA_EXPIRATION = 2;

    /**
     * 短信验证码 redis key
     */
    public static final String SMS_CAPTCHA_CODE_KEY = "sms_captcha_codes:";

    /**
     * 短信验证码有效期（分钟）
     */
    public static final Integer SMS_CAPTCHA_EXPIRATION = 5;

    /**
     * 处理excel中if的处理器名称
     */
    public static final String EXCEL_NO_IF = "excel_noif";

    /**
     * 处理user.auth中if的处理器名称
     */
    public static final String USER_NO_IF = "user_noif";

    /**
     * 处理college中if的处理器名称
     */
    public static final String COLLEGE_NO_IF = "college_noif";

    /**
     * 处理excel中if的处理器名称
     */
    public static final String REFLECTION_NO_IF = "reflection_noif";

    /**
     * 处理menu中id的处理器名称
     */
    public static final String MENU_NO_IF = "menu_noif";

    /**
     * 参数管理 cache key
     */
    public static final String SYS_CONFIG_KEY = "system_config:";

    /**
     * 字典管理 cache key
     */
    public static final String SYS_DICT_KEY = "system_dict:";

    /**
     * 登录失败
     */
    public static final String LOGIN_FAIL = "Error";

    /**
     * 登录成功
     */
    public static final String LOGIN_SUCCESS = "Success";


    /**
     * 通用成功标识
     */
    public static final Integer SUCCESS = 1;

    /**
     * 通用失败标识
     */
    public static final Integer FAIL = 0;

    /**
     * 资源映射路径 前缀
     */
    public static final String RESOURCE_PREFIX = "/profile";
}
