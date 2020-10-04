package run.app.step.project.system.entity.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * @author lingSong
 * @date 2020/8/15 20:25
 */
@Data
@ToString
public class LoginParam {

    @NotBlank(message = "用户名或邮箱不能为空")
    @Size(max = 50, message = "用户名或邮箱的字符长度不能超过{max}")
    @ApiModelProperty(value = "用户名或邮箱")
    private String username;

    @NotBlank(message = "密码不能为空")
    @Size(max = 100, message = "用户登录密码字符长度不能超过{max}")
    @ApiModelProperty(value = "用户密码")
    private String password;

    @NotBlank(message = "验证码不能为空")
    @Size(max = 10, message = "验证码不能为空")
    @ApiModelProperty(value = "验证码")
    private String code;

    @ApiModelProperty(value = "校验验证码唯一标识")
    private String uuid;
}
