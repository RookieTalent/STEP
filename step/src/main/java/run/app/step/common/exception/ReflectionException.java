package run.app.step.common.exception;

import org.springframework.http.HttpStatus;

/**
 * @author lingSong
 * @date 2020/9/1 10:37
 */
public class ReflectionException extends BaseException{

    public ReflectionException(String message) {
        super(message);
    }

    public ReflectionException(String message, Throwable cause) {
        super(message, cause);
    }

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.INTERNAL_SERVER_ERROR;
    }
}
