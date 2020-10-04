package run.app.step.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

/**
 * @author lingSong
 * @date 2020/8/15 11:07
 */
public abstract class BaseException extends RuntimeException {

    /*错误对象*/
    private Object errorObject;

    public BaseException(String message) {
        super(message);
    }

    public BaseException(String message, Throwable cause) {
        super(message, cause);
    }

    @NonNull
    public abstract HttpStatus getStatus();

    @Nullable
    public Object getErrorObject(){
        return errorObject;
    }

    @NonNull
    public BaseException setErrorObject(Object errorObject){
        this.errorObject = errorObject;
        return this;
    }
}
