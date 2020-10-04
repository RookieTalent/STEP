package run.app.step.common.enums.code;

import org.springframework.http.HttpStatus;
import run.app.step.common.enums.code.base.ResponseCodeInterface;

/**
 *
 * 数据库错误为30000
 *
 * @author lingSong
 * @date 2020/8/29 10:27
 */
public enum  DBResponseCode implements ResponseCodeInterface {
    DELETE_ERROR(HttpStatus.BAD_REQUEST, 30001, "删除数据失败"),
    INSERT_ERROR(HttpStatus.BAD_REQUEST, 30002, "新增数据失败")
    ;

    private HttpStatus httpStatus;

    private final int code;

    private final String msg;

    DBResponseCode(HttpStatus httpStatus, int code, String msg){
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
