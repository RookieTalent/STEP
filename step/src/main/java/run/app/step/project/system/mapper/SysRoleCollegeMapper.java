package run.app.step.project.system.mapper;

import org.springframework.stereotype.Repository;
import run.app.step.project.system.entity.SysRoleCollege;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.ArrayList;

/**
 * <p>
 * 角色和学院关联表 Mapper 接口
 * </p>
 *
 * @author lingSong
 * @since 2020-09-29
 */
@Repository
public interface SysRoleCollegeMapper extends BaseMapper<SysRoleCollege> {

    /**
     * 删除角色与学院关联
     *
     * @param id
     */
    void deleteRoleCollegeByRoleId(Long id);

    /**
     * 批量插入数据
     *
     * @param list
     * @return
     */
    int batchRoleCollege(ArrayList<SysRoleCollege> list);

}
