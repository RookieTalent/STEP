package run.app.step.project.system.entity.param.system.dict;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import run.app.step.framework.aspectj.lang.annotation.Excel;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lingSong
 * @date 2020/9/22 20:42
 */
@Data
@ToString
public class DictTypeQuery {

    @ApiModelProperty(value = "字典名称")
    private String dictName;

    @ApiModelProperty(value = "字典类型")
    private String dictType;

    @ApiModelProperty(value = "状态1正常 0停用")
    private Integer status;

    @ApiModelProperty(value = "查询开始时间")
    private String beginTime;

    @ApiModelProperty(value = "查询开始时间")
    private String endTime;

    @ApiModelProperty(value = "当前页")
    private Integer pageNum;

    @ApiModelProperty(value = "当前页数")
    private Integer pageSize;
}
