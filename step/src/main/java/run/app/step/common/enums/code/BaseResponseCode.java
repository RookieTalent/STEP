package run.app.step.common.enums.code;

import org.springframework.http.HttpStatus;
import run.app.step.common.enums.code.base.ResponseCodeInterface;

/**
 * @author lingSong
 * @date 2020/8/15 11:13
 */
public enum BaseResponseCode implements ResponseCodeInterface {
    SUCCESS(HttpStatus.OK,0, "操作成功")
    ;

    private HttpStatus httpStatus;

    private final int code;

    private final String msg;

    BaseResponseCode(HttpStatus httpStatus, int code, String msg) {
        this.httpStatus = httpStatus;
        this.code = code;
        this.msg = msg;
    }

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.OK;
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
