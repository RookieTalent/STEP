package run.app.step.project.system.entity.vo.system.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import run.app.step.framework.aspectj.lang.annotation.Excel;
import run.app.step.project.system.entity.SysUserInfo;

import java.util.Date;

/**
 *
 * TODO 加入学院属性
 *
 * @author lingSong
 * @date 2020/8/24 15:53
 */
@Data
@ToString
@ApiModel(value = "用户列表单位类")
public class UserVO{

    @ApiModelProperty(value = "用户ID")
    private String id;

    @ApiModelProperty(value = "用户手机号")
    @Excel(name = "手机号码")
    private String mobile;

    @ApiModelProperty(value = "用户邮箱")
    @Excel(name = "用户邮箱")
    private String email;

    @ApiModelProperty(value = "用户昵称")
    @Excel(name = "用户昵称")
    private String nickname;

    @ApiModelProperty(value = "用户性别 0女 1男")
    @Excel(name = "用户性别", cellType = Excel.ColumnType.NUMERIC, readConverterExp = "0=女, 1=男")
    private Integer sex;

    @ApiModelProperty(value = "用户年龄")
    @Excel(name = "用户年龄", cellType = Excel.ColumnType.NUMERIC)
    private Integer age;

    @ApiModelProperty(value = "是否禁用 0（false）禁用 1（true）未禁用")
    @Excel(name = "账号状态", cellType = Excel.ColumnType.NUMERIC, readConverterExp = "0=禁用 1=正常")
    private Integer isDisabled;

    @ApiModelProperty(value = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date gmtCreate;

    @ApiModelProperty(value = "用户个人信息")
    private SysUserInfo info;



}
