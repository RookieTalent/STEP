package run.app.step.common.exception;

import org.springframework.http.HttpStatus;

/**
 *
 *
 * @author lingSong
 * @date 2020/8/15 22:10
 */
public class NotFoundException extends BaseException {
    public NotFoundException(String message) {
        super(message);
    }

    public NotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.NOT_FOUND;
    }
}
