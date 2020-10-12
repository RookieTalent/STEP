package run.app.step.project.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import run.app.step.common.constants.Constants;
import run.app.step.common.constants.UserConstants;
import run.app.step.common.exception.DBException;
import run.app.step.common.exception.NotFoundException;
import run.app.step.common.utils.ServiceUtil;
import run.app.step.common.utils.StringUtils;
import run.app.step.framework.factory.NoIfFactory;
import run.app.step.framework.factory.handler.AbstractNoIfHandler;
import run.app.step.project.system.entity.SysCollege;
import run.app.step.project.system.entity.SysUser;
import run.app.step.project.system.entity.SysUserCollege;
import run.app.step.project.system.entity.param.system.college.CollegeQuery;
import run.app.step.project.system.entity.param.system.college.SysCollegeParam;
import run.app.step.project.system.entity.vo.TreeSelect;
import run.app.step.project.system.mapper.SysCollegeMapper;
import run.app.step.project.system.mapper.SysUserCollegeMapper;
import run.app.step.project.system.mapper.SysUserMapper;
import run.app.step.project.system.service.SysCollegeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author lingSong
 * @since 2020-09-07
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class SysCollegeServiceImpl extends ServiceImpl<SysCollegeMapper, SysCollege> implements SysCollegeService {

    @Autowired
    private SysCollegeMapper collegeMapper;

    @Autowired
    private SysUserMapper userMapper;

    @Autowired
    private SysUserCollegeMapper userCollegeMapper;


    @Override
    public List<SysCollege> selectCollegeList(CollegeQuery collegeQuery) {
        return collegeMapper.selectCollegeList(collegeQuery);
    }

    @Override
    public List<TreeSelect> buildCollegeTreeSelect(List<SysCollege> colleges) {
        List<SysCollege> collegeeTree = buildCollegeTree(colleges);
        return collegeeTree.stream().map(TreeSelect::new).collect(Collectors.toList());
    }

    @Override
    public String checkCollegeNameUnique(SysCollege college) {
        SysCollege info = collegeMapper.checkCollegeNameUnique(college.getCollegeName(), college.getParentId());
        if(StringUtils.isNotNull(info)){
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    @Override
    public void insertCollege(SysCollegeParam  collegeParam) {
        SysCollege college = collegeParam.convertTo();

        final String mobile = collegeParam.getMobile();
        final String  parentId = college.getParentId();

        SysUser user = userMapper.findByMobile(mobile);
        if(StringUtils.isNull(user)){
            throw new NotFoundException("未查询到该用户，请重新选择");
        }

        if(parentId != "0"){
            SysCollege info = collegeMapper.selectCollegeById(college.getParentId());
            if(!UserConstants.COLLEGE_NORMAL.equals(String.valueOf(info.getStatus()))){
                throw new DBException("部门停用， 不允许新增");
            }
        }

        ServiceUtil.throwsException(collegeMapper.insert(college), "新增学院数据失败");

        SysUserCollege join = convertToVO(college, user);
        ServiceUtil.throwsException(userCollegeMapper.insert(join), "新增学院关联表数据失败");
    }

    @Override
    public SysCollege selectCollegeById(String id) {
        return collegeMapper.selectCollegeById(id);
    }

    @Override
    public void updateCollege(SysCollege college) {
        // 校验
        AbstractNoIfHandler strategy = NoIfFactory.getInvokeStrategy(Constants.COLLEGE_NO_IF);
        strategy.updateCollegeNoif(college);

        SysCollege info = selectCollegeById(college.getId());
        SysUser olduser = userMapper.findByNickName(info.getLeader());

        // 修改college表
        ServiceUtil.throwsException(collegeMapper.updateById(college), "修改学院信息失败");
        // 修改关联表
        if(!info.getLeader().equals(college.getLeader())){
            SysUser user = userMapper.findByNickName(college.getLeader());
            // user_college Object
            SysUserCollege ucObject = userCollegeMapper.selectByInfo(info.getId(), olduser.getId());
            ServiceUtil.throwsException(userCollegeMapper.updateById(ucObject.setUserId(user.getId())), "新增学院关联表数据失败");
        }
    }

    @Override
    public boolean hasChildByCollegeId(String id) {
        int result = collegeMapper.hashChildByCollegeId(id);
        return result > 0 ? true : false;
    }

    @Override
    public boolean checkDeptExistUser(String id) {
        int result = collegeMapper.checkDeptExistUser(id);
        return result > 0 ? true : false;
    }

    @Override
    public void deleteById(String id) {
        AbstractNoIfHandler strategy = NoIfFactory.getInvokeStrategy(Constants.COLLEGE_NO_IF);
        strategy.deleteCollegeNoif(id);

        SysCollege college = collegeMapper.selectCollegeById(id);
        SysUser user = userMapper.findByNickName(college.getLeader());
        SysUserCollege ucObject = userCollegeMapper.selectByInfo(college.getId(), user.getId());

        collegeMapper.deleteById(id);
        userCollegeMapper.deleteById(ucObject.getId());
    }

    @Override
    public List<Integer> selectCollegeByRoleId(Long id) {
        return collegeMapper.selectCollegeByRoleId(id);
    }


    /**
     * 构建学院树 归类children
     *
     * @param collegeList
     * @return
     */
    public List<SysCollege> buildCollegeTree(List<SysCollege> collegeList) {
        List<SysCollege> returnList = new ArrayList<>();
        List<String> tempList = new ArrayList<>();

        collegeList.stream().forEach(college -> tempList.add(college.getId()));

        for (Iterator<SysCollege> iterator = collegeList.iterator(); iterator.hasNext();){
            SysCollege college = (SysCollege)iterator.next();
            // 如果是顶级节点 遍历该父节点下的所有子节点
            if(!tempList.contains(college.getParentId())){
                recursionFn(collegeList, college);
                returnList.add(college);
            }
        }
        // 若都是顶级节点 直接返回即可
        if(returnList.isEmpty()){
            returnList = collegeList;
        }

        return returnList;
    }


    /**
     * 递归列表
     * @param list
     * @param target
     */
    private void recursionFn(List<SysCollege> list, SysCollege target){
        // 得到子节点
        List<SysCollege> childList = getChildList(list, target);
        target.setChildren(childList);
    }

    /**
     * 得到子节点列表
     * @param list
     * @param target
     * @return
     */
    private List<SysCollege> getChildList(List<SysCollege> list, SysCollege target){
        List<SysCollege> tlist = new ArrayList<>();

        list.stream().forEach(college -> {
            if(StringUtils.isNotNull(college.getParentId()) && Objects.equals(college.getParentId(), target.getId())){
                tlist.add(college);
            }
        });

        return tlist;
    }

    /**
     * 判断是否有子节点
     * @param list
     * @param target
     * @return
     */
    private boolean hasChild(List<SysCollege> list, SysCollege target){
        return getChildList(list, target).size()>0 ? true : false;
    }

    /**
     * 生成联结表对象
     *
     * @param college
     * @param user
     * @return
     */
    private SysUserCollege convertToVO(SysCollege college, SysUser user){
        SysUserCollege target = new SysUserCollege();
        final String collegeId = college.getId();

        target.setCollegeId(collegeId);
        target.setUserId(user.getId());
        return target;
    }
}
