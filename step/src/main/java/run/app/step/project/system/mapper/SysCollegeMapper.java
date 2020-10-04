package run.app.step.project.system.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import run.app.step.project.system.entity.SysCollege;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import run.app.step.project.system.entity.param.system.college.CollegeQuery;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author lingSong
 * @since 2020-09-07
 */
@Repository
public interface SysCollegeMapper extends BaseMapper<SysCollege> {

    /**
     *查询学院列表数据
     *
     * @param collegeQuery
     * @return
     */
    List<SysCollege> selectCollegeList(CollegeQuery collegeQuery);

    /**
     * 校验学院名称是否唯一
     *
     * @param collegeName
     * @param parentId
     * @return
     */
    SysCollege checkCollegeNameUnique(@Param("collegeName") String collegeName, @Param("parentId") String parentId);

    /**
     * 检测该父id的学院是否存在
     *
     * @param id
     * @return
     */
    SysCollege selectCollegeById(String id);

    /**
     *  是否存在子节点
     *
     * @param id
     * @return
     */
    int hashChildByCollegeId(String id);

    /**
     * 查询部门是否存在用户
     *
     * @param id
     * @return
     */
    int checkDeptExistUser(String id);
}
