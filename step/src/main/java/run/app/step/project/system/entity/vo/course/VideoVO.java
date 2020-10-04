package run.app.step.project.system.entity.vo.course;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

/**
 * @author lingSong
 * @date 2020/9/21 10:45
 */
@Data
@ToString
@AllArgsConstructor
public class VideoVO {
    @ApiModelProperty(value = "云端视频资源")
    private String videoSourceId;

    @ApiModelProperty(value = "原始文件名称")
    private String videoOriginalName;

    @ApiModelProperty(value = "视频时长(单位秒)")
    private Long duration;

    @ApiModelProperty(value = "视频资源大小")
    private Long size;
}
