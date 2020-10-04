package run.app.step.common.exception;

import org.springframework.http.HttpStatus;

/**
 * @author lingSong
 * @date 2020/8/16 14:58
 */
public class CaptchaException extends BaseException {
    public CaptchaException(String message) {
        super(message);
    }

    public CaptchaException(String message, Throwable cause) {
        super(message, cause);
    }

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.NOT_FOUND;
    }
}
