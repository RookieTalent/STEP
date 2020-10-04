package run.app.step.project.system.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import run.app.step.framework.aspectj.lang.annotation.Excel;

/**
 * <p>
 * 
 * </p>
 *
 * @author lingSong
 * @since 2020-09-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="SysDictType对象", description="")
public class SysDictType implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键id")
    @TableId(value = "id", type = IdType.AUTO)
    @Excel(name = "字典主键", cellType = Excel.ColumnType.NUMERIC)
    private Long id;

    @ApiModelProperty(value = "字典名称")
    @Excel(name = "字典名称")
    private String dictName;

    @ApiModelProperty(value = "字典类型")
    @Excel(name = "字典类型")
    private String dictType;

    @ApiModelProperty(value = "状态1正常 0停用")
    @Excel(name = "状态", readConverterExp = "1=正常,0=停用")
    private Integer status;

    @ApiModelProperty(value = "创建者")
    private String createBy;

    @ApiModelProperty(value = "更新者")
    private String updateBy;

    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date gmtCreate;

    @ApiModelProperty(value = "更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date gmtModified;

    @ApiModelProperty(value = "备注")
    private String remark;


}
