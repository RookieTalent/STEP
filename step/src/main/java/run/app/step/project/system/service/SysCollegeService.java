package run.app.step.project.system.service;

import run.app.step.project.system.entity.SysCollege;
import com.baomidou.mybatisplus.extension.service.IService;
import run.app.step.project.system.entity.param.system.college.CollegeQuery;
import run.app.step.project.system.entity.param.system.college.SysCollegeParam;
import run.app.step.project.system.entity.vo.TreeSelect;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author lingSong
 * @since 2020-09-07
 */
public interface SysCollegeService extends IService<SysCollege> {

    /**
     * 获取学院列表
     * @param collegeQuery 查询参数
     * @return
     */
    List<SysCollege> selectCollegeList(CollegeQuery collegeQuery);

    /**
     * 构建前端所需的树结构
     * @param colleges
     * @return
     */
    List<TreeSelect> buildCollegeTreeSelect(List<SysCollege> colleges);

    /**
     * 校验学院名称是否唯一
     *
     * @param college
     * @return
     */
    String checkCollegeNameUnique(SysCollege college);

    /**
     * 新增学院信息
     *
     * @param collegeParam
     */
    void insertCollege(SysCollegeParam collegeParam);

    /**
     * 根据id查询学院信息
     *
     * @param id
     * @return
     */
    SysCollege selectCollegeById(String id);

    /**
     * 修改学院信息以及关联表信息
     *
     * @param college
     */
    void updateCollege(SysCollege college);

    /**
     * 是否存在学院子节点
     *
     * @param id
     * @return
     */
    boolean hasChildByCollegeId(String id);

    /**
     *  查询部门是否存在用户
     *
     * @param id
     * @return
     */
    boolean checkDeptExistUser(String id);

    /**
     * 根据id删除学院信息
     *
     * @param id
     */
    void deleteById(String id);

    /**
     * 根据角色id查询部门树信息
     *
     * @param id
     * @return
     */
    List<Integer> selectCollegeByRoleId(Long id);
}
