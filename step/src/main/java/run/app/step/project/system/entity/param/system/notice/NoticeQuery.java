package run.app.step.project.system.entity.param.system.notice;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

/**
 * @author lingSong
 * @date 2020/10/12 21:38
 */
@Data
@ToString
public class NoticeQuery {

    @ApiModelProperty(value = "当前页")
    private Integer pageNum;

    @ApiModelProperty(value = "当前页数")
    private Integer pageSize;

    @ApiModelProperty(value = "公告标题")
    private String noticeTitle;

    @ApiModelProperty(value = "公告类型（1通知 2公告）")
    private Integer noticeType;

    @ApiModelProperty(value = "创建者")
    private String createBy;

    @ApiModelProperty(value = "状态")
    private String status;
}
