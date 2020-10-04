package run.app.step.project.system.entity.vo;

import lombok.Data;
import lombok.ToString;
import org.springframework.http.HttpStatus;

import java.util.List;

/**
 * @author lingSong
 * @date 2020/8/25 16:25
 */
@Data
@ToString
public class PageVO {

    /** 状态码*/
    private HttpStatus httpStatus;

    /*自定义状态码*/
    private Integer code = 0;

    /*操作信息*/
    private String msg;

    /** 总记录数*/
    private Long total;

    /** 列表数据*/
    private List<?> rows;
}
