package run.app.step.common.exception;

import org.springframework.http.HttpStatus;

/**
 * @author lingSong
 * @date 2020/9/2 14:33
 */
public class AlreadyExistsException extends BaseException {
    public AlreadyExistsException(String message) {
        super(message);
    }

    public AlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.BAD_REQUEST;
    }
}
