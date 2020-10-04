package run.app.step.common.support;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.http.HttpStatus;
import run.app.step.common.enums.code.BaseResponseCode;
import run.app.step.common.enums.code.base.ResponseCodeInterface;

import java.util.HashMap;
import java.util.Map;


/**
 * @author lingSong
 * @date 2020/8/15 11:00
 */
@Data
public class AjaxResult{

    /* 状态码 */
    @ApiModelProperty(value = "请求响应状态码", name = "httpStatus")
    private HttpStatus httpStatus;

    /*自定义状态码*/
    @ApiModelProperty(value = "自定义状态码 code=0成功 其他失败", name = "code")
    private int code = 0;

    /*操作信息*/
    @ApiModelProperty(value = "操作信息", name = "msg")
    private String msg;

    /*响应内容 code为0时返回为空*/
    @ApiModelProperty(value = "响应内容", name = "data")
    private Map<String, Object> data = new HashMap<>();


    /*构造方法*/
    public AjaxResult(HttpStatus httpStatus, int code, String msg) {
        this.httpStatus = httpStatus;
        this.code = code;
        this.msg = msg;
    }

    public AjaxResult(HttpStatus httpStatus, int code, String msg, Map<String, Object> data) {
        this.httpStatus = httpStatus;
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    /*封装*/
    public AjaxResult(){
        this.httpStatus = BaseResponseCode.SUCCESS.getStatus();
        this.code = BaseResponseCode.SUCCESS.getCode();
        this.msg = BaseResponseCode.SUCCESS.getMsg();
    }

    public AjaxResult(Map<String, Object> data){
        this.httpStatus = BaseResponseCode.SUCCESS.getStatus();
        this.code = BaseResponseCode.SUCCESS.getCode();
        this.msg = BaseResponseCode.SUCCESS.getMsg();
        this.data = data;
    }

    /*适用于异常*/
    public AjaxResult(ResponseCodeInterface responseCodeInterface){
        this.httpStatus = responseCodeInterface.getStatus();
        this.code = responseCodeInterface.getCode();
        this.msg = responseCodeInterface.getMsg();
    }

    public AjaxResult(ResponseCodeInterface responseCodeInterface, Map<String, Object> data){
        this.httpStatus = responseCodeInterface.getStatus();
        this.code = responseCodeInterface.getCode();
        this.msg = responseCodeInterface.getMsg();
        this.data = data;
    }


    public static AjaxResult ok(){
        return new AjaxResult();
    }

    public static AjaxResult error(ResponseCodeInterface responseCodeInterface){
        return new AjaxResult(responseCodeInterface);
    }

    /*链式设置data数据*/
    public AjaxResult data(String key, Object value){
        this.data.put(key, value);
        return this;
    }

    public AjaxResult data(Map<String, Object> data){
        this.setData(data);
        return this;
    }

    public AjaxResult setMsg(String msg) {
        this.msg = msg;
        return this;
    }
}
