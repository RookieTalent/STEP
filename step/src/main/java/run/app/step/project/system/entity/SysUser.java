package run.app.step.project.system.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;
import run.app.step.common.utils.StringUtils;
import run.app.step.framework.aspectj.lang.annotation.Excel;

/**
 *
 * @author lingSong
 * @since 2020-08-14
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="SysUser对象", description="SysUser对象")
public class SysUser {

    //private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户/管理员id")
    @TableId(value = "id", type = IdType.ID_WORKER_STR)
    private String id;

    @ApiModelProperty(value = "gitHub_openid")
    private String githubId = "1";      //TODO 默认值为1

    @ApiModelProperty(value = "用户手机号")
    @Excel(name = "手机号码")
    private String mobile;

    @ApiModelProperty(value = "用户邮箱")
    @Excel(name = "用户邮箱")
    private String email;

    @ApiModelProperty(value = "用户昵称")
    @Excel(name = "用户昵称")
    private String nickname;

    @ApiModelProperty(value = "用户密码")
    @Excel(name = "用户密码")
    private String password;

    @ApiModelProperty(value = "用户性别 0女, 1男")
    @Excel(name = "用户性别", cellType = Excel.ColumnType.NUMERIC, readConverterExp = "0=女, 1=男")
    private Integer sex;

    @ApiModelProperty(value = "用户年龄")
    @Excel(name = "用户年龄", cellType = Excel.ColumnType.NUMERIC)
    private Integer age;

    @ApiModelProperty(value = "用户头像地址")
    private String avatar;

    @ApiModelProperty(value = "签名")
    private String sign;

    @ApiModelProperty(value = "是否禁用 0（false）禁用 1（true）未禁用")
    @Excel(name = "账号状态", cellType = Excel.ColumnType.NUMERIC, readConverterExp = "0=禁用 1=正常")
    private Integer isDisabled;

    @ApiModelProperty(value = "逻辑删除 0（已删除） 1（未删除）")
    private Integer isDeleted;

    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private Date gmtCreate;

    @ApiModelProperty(value = "更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date gmtModified;

    @ApiModelProperty(value = "学院对象")
    @TableField(exist = false)
    private SysCollege college;

    @ApiModelProperty(value = "角色对象")
    @TableField(exist = false)
    private List<SysRole> roles;

    @ApiModelProperty(value = "角色组")
    @TableField(exist = false)
    private Long[] roleIds;

    //TODO 暂定
    public static boolean isAdmin(String userId){
        return StringUtils.isNotNull(userId) && "1294622577226002433".equals(userId);
    }
}
