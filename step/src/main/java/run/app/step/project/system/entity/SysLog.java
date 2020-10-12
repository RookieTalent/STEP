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
 * 系统日志
 * </p>
 *
 * @author lingSong
 * @since 2020-10-09
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="SysLog对象", description="系统日志")
public class SysLog implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    @Excel(name = "操作序号", cellType = Excel.ColumnType.NUMERIC)
    private Long id;

    @ApiModelProperty(value = "用户id")
    private String userId;

    @ApiModelProperty(value = "用户名")
    @Excel(name = "操作人员")
    private String nickname;

    @ApiModelProperty(value = "用户操作")
    @Excel(name = "操作模块")
    private String operation;

    @ApiModelProperty(value = "响应时间")
    @Excel(name = "耗时", cellType = Excel.ColumnType.NUMERIC)
    private Integer time;

    @ApiModelProperty(value = "请求方法")
    @Excel(name = "请求方法")
    private String method;

    @ApiModelProperty(value = "请求方式")
    @Excel(name = "请求方式")
    private String methodMode;

    @ApiModelProperty(value = "请求参数")
    @Excel(name = "请求擦书")
    private String params;

    @ApiModelProperty(value = "IP地址")
    @Excel(name = "请求地址")
    private String ip;

    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private Date gmtCreate;

    @ApiModelProperty(value = "查询开始时间")
    @TableField(exist = false)
    private String beginTime;

    @ApiModelProperty(value = "查询开始时间")
    @TableField(exist = false)
    private String endTime;

}
