package run.app.step.common.exception;

import org.springframework.http.HttpStatus;

/**
 * @author lingSong
 * @date 2020/8/28 15:05
 */
public class RedisException extends BaseException {
    public RedisException(String message) {
        super(message);
    }

    public RedisException(String message, Throwable cause) {
        super(message, cause);
    }

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.INTERNAL_SERVER_ERROR;
    }
}
