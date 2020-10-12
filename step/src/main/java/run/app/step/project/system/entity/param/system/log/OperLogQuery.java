package run.app.step.project.system.entity.param.system.log;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import run.app.step.common.enums.code.BusinessType;
import run.app.step.common.utils.StringUtils;
import run.app.step.common.utils.converter.InputConverter;
import run.app.step.project.system.entity.SysLog;

/**
 * @author lingSong
 * @date 2020/10/10 14:52
 */
@Data
@ToString
public class OperLogQuery implements InputConverter<SysLog> {

    @ApiModelProperty(value = "操作模块")
    private String operation;

    @ApiModelProperty(value = "操作者")
    private String nickname;

    @ApiModelProperty(value = "方法方式")
    private String methodMode;

    @ApiModelProperty(value = "当前页")
    private Integer pageNum;

    @ApiModelProperty(value = "当前页数")
    private Integer pageSize;

    @ApiModelProperty(value = "查询开始时间")
    private String beginTime;

    @ApiModelProperty(value = "查询开始时间")
    private String endTime;


    @Override
    public SysLog convertTo() {
        if(StringUtils.isNotEmpty(this.methodMode)){
            switch (this.methodMode){
                case "1": this.methodMode = BusinessType.LIST.toString();
                    break;
                case "2": this.methodMode = BusinessType.INSERT.toString();
                    break;
                case "3": this.methodMode = BusinessType.UPDATE.toString();
                    break;
                case "4": this.methodMode = BusinessType.DELETE.toString();
                    break;
                case "5": this.methodMode = BusinessType.GRANT.toString();
                    break;
                case "6": this.methodMode = BusinessType.EXPORT.toString();
                    break;
                case "7": this.methodMode = BusinessType.IMPORT.toString();
                    break;
                case "8": this.methodMode = BusinessType.FORCE.toString();
                    break;
                case "9": this.methodMode = BusinessType.CLEAN.toString();
                    break;
            }
        }

        return InputConverter.super.convertTo();
    }
}
