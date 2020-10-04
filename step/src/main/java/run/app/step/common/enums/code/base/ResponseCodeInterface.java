package run.app.step.common.enums.code.base;

import org.springframework.http.HttpStatus;

/**
 * @author lingSong
 * @date 2020/8/15 11:12
 */
public interface ResponseCodeInterface {
    HttpStatus getStatus();

    int getCode();

    String getMsg();
}
