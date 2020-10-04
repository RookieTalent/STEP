package run.app.step.project.system.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * @author lingSong
 * @date 2020/9/25 17:47
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="SysUserInfo对象", description="")
public class SysUserInfo implements Serializable {

    @ApiModelProperty(value = "主键ID")
    private String id;

    @ApiModelProperty(value = "昵称")
    private String nickname;

    @ApiModelProperty(value = "资历,一句话说明用户")
    private String career;

    @ApiModelProperty(value = "0=副教授  1=教授")
    private Integer level;

    @ApiModelProperty(value = "用户简介")
    private String intro;

    @ApiModelProperty(value = "创建时间")
    private Date gmtCreate;

    @ApiModelProperty(value = "更新时间")
    private Date gmtModified;
}
