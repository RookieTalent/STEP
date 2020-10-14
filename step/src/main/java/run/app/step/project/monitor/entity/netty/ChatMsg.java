package run.app.step.project.monitor.entity.netty;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author lingSong
 * @date 2020/10/12 14:43
 */
@Data
@ToString
public class ChatMsg implements Serializable {

    @ApiModelProperty(value = "发送者id")
    private String senderId;

    @ApiModelProperty(value = "接受者id")
    private String receiverId;

    @ApiModelProperty(value = "聊天内容")
    private String msg;

    @ApiModelProperty(value = "消息id 用于消息签收")
    private String msgId;
}
