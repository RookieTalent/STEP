package run.app.step.project.system.entity.param.system.role;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

/**
 * @author lingSong
 * @date 2020/10/6 10:35
 */
@Data
@ToString
public class RoleQuery {
    @ApiModelProperty(value = "当前页")
    private Integer pageNum;

    @ApiModelProperty(value = "当前页数")
    private Integer pageSize;

    @ApiModelProperty(value = "角色名称")
    private String roleName;

    @ApiModelProperty(value = "角色权限字符串")
    private String roleKey;

    @ApiModelProperty(value = "角色状态（1正常 0停用）")
    private Integer status;

    @ApiModelProperty(value = "查询开始时间")
    private String beginTime;

    @ApiModelProperty(value = "查询开始时间")
    private String endTime;
}
