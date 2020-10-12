package run.app.step.project.system.entity.param.system.user;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import org.apache.commons.lang3.StringUtils;
import run.app.step.common.utils.converter.InputConverter;
import run.app.step.project.system.entity.SysCollege;
import run.app.step.project.system.entity.SysRole;
import run.app.step.project.system.entity.SysUser;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * @author lingSong
 * @date 2020/8/25 15:58
 */
@Data
@ToString
@ApiModel("新增用户类")
public class SysUserParam implements InputConverter<SysUser> {

    @ApiModelProperty(value = "用户id")
    private String id;

    @ApiModelProperty(value = "用户手机号")
    @NotBlank(message = "用户手机号不能为空")
    @Size(max = 11, message = "用户手机号长度不能超过{max}")
    private String mobile;

    @ApiModelProperty(value = "用户邮箱")
    @NotBlank(message = "用户邮箱不能为空")
    @Size(max = 50, message = "用户邮箱长度不能超过{max}")
    private String email;

    @ApiModelProperty(value = "用户昵称")
    @NotBlank(message = "用户昵称不能为空")
    @Size(max = 50, message = "用户昵称的字符长度不能超过{max}")
    private String nickname;

    @ApiModelProperty(value = "用户密码")
    private String password;

    @ApiModelProperty(value = "用户性别 0女 1男")
    private Integer sex;

    @ApiModelProperty(value = "用户年龄")
    private Integer age;

    @ApiModelProperty(value = "用户头像地址")
    private String avatar;

    @ApiModelProperty(value = "学院对象")
    private SysCollege college;

    @ApiModelProperty(value = "角色对象")
    private List<SysRole> roles;

    @ApiModelProperty(value = "角色组")
    private Long[] roleIds;

    @Override
    public SysUser convertTo() {

        if(StringUtils.isEmpty(avatar)){
            avatar = "https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif";
        }

        return InputConverter.super.convertTo();
    }
}
