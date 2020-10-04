package run.app.step.project.system.entity.param.system.menu;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

/**
 * @author lingSong
 * @date 2020/9/14 20:05
 */
@Data
@ToString
public class MenuQuery {

    @ApiModelProperty(value = "菜单名称")
    private String menuName;

    @ApiModelProperty(value = "菜单状态（1显示 0隐藏）")
    private Integer visible;
}
