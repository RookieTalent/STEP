package run.app.step.framework.factory.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import run.app.step.common.constants.Constants;
import run.app.step.common.constants.UserConstants;
import run.app.step.common.exception.AlreadyExistsException;
import run.app.step.common.exception.BadRequestException;
import run.app.step.common.exception.NotFoundException;
import run.app.step.framework.factory.NoIfFactory;
import run.app.step.project.system.entity.SysCollege;
import run.app.step.project.system.service.SysCollegeService;
import run.app.step.project.system.service.SysUserService;

/**
 * @author lingSong
 * @date 2020/9/10 20:53
 */
@Component
public class CollegeNoIfHandler extends AbstractNoIfHandler{

    @Autowired
    private SysCollegeService collegeService;

    @Autowired
    private SysUserService userService;

    /**
     * TODO 可能还有未想到的
     */
    @Override
    public void updateCollegeNoif(SysCollege college) {
        // 修改的学院名不能再存在
        if(UserConstants.NOT_UNIQUE.equals(collegeService.checkCollegeNameUnique(college))){
            throw new AlreadyExistsException("修改学院" + college.getCollegeName() + " 失败， 该学院名已存在");
        }
        // 修改的负责人不存在
        if(UserConstants.UNIQUE.equals(userService.checkMobileUnique(college.getMobile()))){
            throw new NotFoundException("修改的" + college.getMobile() + "未找到， 请重新填写有效用户");
        }
        if(UserConstants.UNIQUE.equals(userService.checkNickNameUnique(college.getLeader()))){
            throw new NotFoundException("修改的" + college.getLeader() + "未找到， 请重新填写有效用户");
        }
        if(UserConstants.UNIQUE.equals(userService.checkEmailUnique(college.getEmail()))){
            throw new NotFoundException("修改的" + college.getEmail() + "未找到， 请重新填写有效用户");
        }
    }

    @Override
    public void deleteCollegeNoif(String id) {
        if(collegeService.hasChildByCollegeId(id)){
            throw new BadRequestException("存在下级学院，不允许删除");
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        NoIfFactory.register(Constants.COLLEGE_NO_IF, this);
    }
}
