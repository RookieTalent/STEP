package run.app.step.project.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
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
 * @since 2020-09-16
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="SysCourse对象", description="")
public class SysCourse implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "课程id（主键）")
    @TableId(value = "id", type = IdType.ID_WORKER_STR)
    private String id;

    @ApiModelProperty(value = "授课用户id")
    private String userId;

    @ApiModelProperty(value = "课程所属学院id（若是某学院通用或全学院通用则标0）")
    private String collegeId;

    @ApiModelProperty(value = "课程标题")
    private String title;

    @ApiModelProperty(value = "课程描述")
    private String description;

    @ApiModelProperty(value = "课程封面")
    private String cover;

    @ApiModelProperty(value = "课程数")
    private Long lessonNum;

    @ApiModelProperty(value = "选课人数")
    private Long joinNum;

    @ApiModelProperty(value = "创建时间")
    private Date gmtCreate;

    @ApiModelProperty(value = "更新时间")
    private Date gmtModified;
}
