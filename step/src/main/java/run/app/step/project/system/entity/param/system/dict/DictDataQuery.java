package run.app.step.project.system.entity.param.system.dict;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

/**
 * @author lingSong
 * @date 2020/9/23 22:13
 */
@Data
@ToString
public class DictDataQuery {

    @ApiModelProperty(value = "字典类型")
    private String dictType;

    @ApiModelProperty(value = "字典标签")
    private String dictLabel;

    @ApiModelProperty(value = "状态1正常 0停用")
    private Integer status;

    @ApiModelProperty(value = "当前页")
    private Integer pageNum;

    @ApiModelProperty(value = "当前页数")
    private Integer pageSize;
}
