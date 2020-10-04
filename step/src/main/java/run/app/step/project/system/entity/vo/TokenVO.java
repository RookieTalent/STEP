package run.app.step.project.system.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

/**
 *
 * 包含access_token和refresh_token
 *
 * @author lingSong
 * @date 2020/8/16 14:52
 */
@Data
@ToString
@ApiModel("令牌类")
public class TokenVO {

    @ApiModelProperty("令牌")
    private String access_token;

    @ApiModelProperty("刷新令牌")
    private String refresh_token;

}
