package run.app.step.project.system.entity.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.ToString;
import run.app.step.project.system.entity.SysCollege;
import run.app.step.project.system.entity.SysMenu;
import run.app.step.project.system.entity.SysUser;
import run.app.step.project.system.entity.vo.system.user.UserVO;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author lingSong
 * @date 2020/9/8 16:33
 */
@Data
@ToString
public class TreeSelect implements Serializable {

    /** 节点ID*/
    private String id;

    /** 节点名称*/
    private String label;

    /** 子节点
     * 前端的朋友要求说尽量不要有null，可有为空串“” 或者 0 或者 []， 但尽量不要null
     * */
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<TreeSelect> children;

    public TreeSelect() {
    }

    public TreeSelect(SysCollege college){
        this.id = college.getId();
        this.label = college.getCollegeName();
        this.children = college.getChildren().stream().map(TreeSelect::new).collect(Collectors.toList());
    }

    public TreeSelect(UserVO user){
        this.id = user.getId();
        this.label = user.getNickname();
        this.children = null;
    }

    public TreeSelect(SysMenu menu){
        this.id = menu.getId();
        this.label = menu.getMenuName();
        this.children = menu.getChildren().stream().map(TreeSelect::new).collect(Collectors.toList());
    }

}
