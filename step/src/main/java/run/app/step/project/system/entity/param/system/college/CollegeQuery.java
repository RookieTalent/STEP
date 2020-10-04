package run.app.step.project.system.entity.param.system.college;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;


/**
 * @author lingSong
 * @date 2020/9/7 15:08
 */
@Data
@ToString
public class CollegeQuery {

    @ApiModelProperty(value = "父级id")
    private String parentId;

    @ApiModelProperty(value = "学院名称")
    private String collegeName;

    @ApiModelProperty(value = "状态")
    private Integer status; //isDisabled
}
