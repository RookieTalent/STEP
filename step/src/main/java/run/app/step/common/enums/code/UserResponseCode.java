package run.app.step.common.enums.code;

import org.springframework.http.HttpStatus;
import run.app.step.common.enums.code.base.ResponseCodeInterface;

/**
 *
 * 用户错误为10000
 *
 * @author lingSong
 * @date 2020/8/26 15:46
 */
public enum  UserResponseCode implements ResponseCodeInterface {
    NickName_EXIST(HttpStatus.FOUND, 10001, "用户昵称已存在"),
    Mobile_EXIST(HttpStatus.FOUND, 10002, "手机号已存在"),
    Email_EXIST(HttpStatus.FOUND, 10003, "邮箱已存在"),
    CAPTCHA_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, 1004, "请求短信失败"),
    Auth_FAIL(HttpStatus.UNAUTHORIZED, 1005, "认证失败")
    ;


    private HttpStatus httpStatus;

    private final int code;

    private final String msg;

    UserResponseCode(HttpStatus httpStatus, int code, String msg){
        this.httpStatus = httpStatus;
        this.code = code;
        this.msg = msg;
    }

    @Override
    public HttpStatus getStatus() {
        return httpStatus;
    }

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public String getMsg() {
        return msg;
    }
}
