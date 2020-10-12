package run.app.step.project.system.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import run.app.step.project.system.entity.SysUserCollege;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author lingSong
 * @since 2020-09-09
 */
@Repository
public interface SysUserCollegeMapper extends BaseMapper<SysUserCollege> {

    /**
     * 根据学院id 与 用户id 查询 关联对象
     * @param college_id
     * @param user_id
     * @return
     */
    SysUserCollege selectByInfo(@Param("college_id") String college_id, @Param("user_id") String user_id);

    /**
     * 删除学院表与用户表相关联的信息
     *
     * @param userId
     */
    void deleteUserCollegeByUserId(String userId);
}
