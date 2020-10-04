package run.app.step.common.exception;

import org.springframework.http.HttpStatus;

/**
 * @author lingSong
 * @date 2020/8/16 14:58
 */
public class BadRequestException extends BaseException {
    public BadRequestException(String message) {
        super(message);
    }

    public BadRequestException(String message, Throwable cause) {
        super(message, cause);
    }

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.BAD_REQUEST;
    }
}
