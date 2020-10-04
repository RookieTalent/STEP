package run.app.step.project.system.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
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
@ApiModel(value="SysDictData对象", description="")
public class SysDictData implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "字典编码")
    @TableId(value = "dict_code", type = IdType.AUTO)
    @Excel(name = "字典编码", cellType = Excel.ColumnType.NUMERIC)
    private Long dictCode;

    @ApiModelProperty(value = "字典排序")
    @Excel(name = "字典排序", cellType = Excel.ColumnType.NUMERIC)
    private Integer dictSort;

    @ApiModelProperty(value = "字典标签")
    @Excel(name = "字典标签")
    private String dictLabel;

    @ApiModelProperty(value = "字典键值")
    @Excel(name = "字典键值")
    private String dictValue;

    @ApiModelProperty(value = "字典类型")
    @Excel(name = "字典类型")
    private String dictType;

    @ApiModelProperty(value = "样式属性（其他样式扩展）")
    private String cssClass;

    @ApiModelProperty(value = "表格回显样式")
    private String listClass;

    @ApiModelProperty(value = "状态1正常 0停用")
    @Excel(name = "状态", readConverterExp = "1=正常,0=停用")
    private Integer status;

    @ApiModelProperty(value = "是否默认（Y是 N否）")
    @Excel(name = "是否默认", readConverterExp = "Y=是,N=否")
    private String isDefault;

    @ApiModelProperty(value = "创建者")
    private String createBy;

    @ApiModelProperty(value = "更新者")
    private String updateBy;

    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private Date gmtCreate;

    @ApiModelProperty(value = "更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date gmtModified;

    @ApiModelProperty(value = "备注")
    private String remark;


}
