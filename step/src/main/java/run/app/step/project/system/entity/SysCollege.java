package run.app.step.project.system.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;

import java.util.ArrayList;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author lingSong
 * @since 2020-09-07
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="SysCollege对象", description="")
public class SysCollege implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "学院及学院下级id")
    @TableId(value = "id", type = IdType.ID_WORKER_STR)
    private String id;

    @ApiModelProperty(value = "父级学院id")
    private String parentId;

    @ApiModelProperty(value = "学院及学院下级名称")
    private String collegeName;

    @ApiModelProperty(value = "负责人")
    private String leader;

    @ApiModelProperty(value = "联系电话")
    private String mobile;

    @ApiModelProperty(value = "用户邮箱")
    private String email;

    @ApiModelProperty(value = "部门状态 （0禁用 1正常）")
    private Integer status; //DONE 这里与treeselect is_disabled属性 起冲突了 所以换成status 无语了

    @ApiModelProperty(value = "删除标志 （0删除 1存在）")
    private Integer isDeleted;

    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private Date gmtCreate;

    @ApiModelProperty(value = "更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date gmtModified;

    @ApiModelProperty(value = "父级学院名称")
    @TableField(exist = false)
    private String parentName;

    @ApiModelProperty(value = "子集")
    @TableField(exist = false)
    private List<SysCollege> children = new ArrayList<>();
}
