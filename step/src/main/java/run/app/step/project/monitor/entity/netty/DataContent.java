package run.app.step.project.monitor.entity.netty;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author lingSong
 * @date 2020/10/12 14:41
 */
@Data
@ToString
public class DataContent implements Serializable {
    @ApiModelProperty(value = "动作类型")
    private Integer action;

    @ApiModelProperty(value = "用户聊天内容")
    private ChatMsg chatMsg;

    @ApiModelProperty(value = "扩展字段")
    private String extand;

}
