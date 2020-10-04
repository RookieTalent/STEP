package run.app.step.project.system.entity.param.system.college;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import run.app.step.common.utils.StringUtils;
import run.app.step.common.utils.converter.InputConverter;
import run.app.step.project.system.entity.SysCollege;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * @author lingSong
 * @date 2020/9/9 21:38
 */
@Data
@ToString
@ApiModel("新增学院类")
public class SysCollegeParam implements InputConverter<SysCollege> {

    @ApiModelProperty(value = "学院id")
    private String id;

    @ApiModelProperty(value = "父级id")
    private String parentId;

    @ApiModelProperty(value = "学院及学院下级名称")
    @NotBlank(message = "部门名称不能为空")
    private String collegeName;

    @ApiModelProperty(value = "负责人")
    @NotBlank(message = "负责人名称不能为空")
    @Size(max = 20, message = "负责人名称字符长度不能超过{max}")
    private String leader;


    @ApiModelProperty(value = "联系电话")
    @NotBlank(message = "联系电话不能为空")
    @Size(max = 11, message = "联系电话字符长度不能超过{max}")
    private String mobile;

    @ApiModelProperty(value = "用户邮箱")
    @NotBlank(message = "联系邮箱不能为空")
    @Size(max = 50, message = "联系邮箱字符长度不能超过{max}")
    private String email;

    @ApiModelProperty(value = "部门状态 （0禁用 1正常）")
    private Integer status;

    @Override
    public SysCollege convertTo() {
        if(StringUtils.isNull(this.parentId)){
            this.parentId = "0";
        }

        return InputConverter.super.convertTo();
    }
}
