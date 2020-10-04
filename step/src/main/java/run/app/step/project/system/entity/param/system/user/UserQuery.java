package run.app.step.project.system.entity.param.system.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.Data;
import lombok.ToString;

/**
 * @author lingSong
 * @date 2020/8/24 15:43
 */
@Data
@ToString
@ApiModel(value = "用户列表查询类")
public class UserQuery {

    @ApiModelProperty(value = "用户昵称")
    private String nickname;

    @ApiModelProperty(value = "手机号")
    private String mobile;

    @ApiModelProperty(value = "当前用户状态:是否禁用 0（false）禁用 1（true）未禁用")
    private Integer isDisabled;

    @ApiModelProperty(value = "学院id")
    private String collegeId;

    @ApiModelProperty(value = "当前页")
    private Integer pageNum;

    @ApiModelProperty(value = "当前页数")
    private Integer pageSize;
}
