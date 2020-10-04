package run.app.step.project.system.service.impl;

import org.springframework.transaction.annotation.Transactional;
import run.app.step.common.exception.DBException;
import run.app.step.project.system.entity.SysRoleCollege;
import run.app.step.project.system.mapper.SysRoleCollegeMapper;
import run.app.step.project.system.service.SysRoleCollegeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 角色和学院关联表 服务实现类
 * </p>
 *
 * @author lingSong
 * @since 2020-09-29
 */
@Service
@Transactional(rollbackFor = DBException.class)
public class SysRoleCollegeServiceImpl extends ServiceImpl<SysRoleCollegeMapper, SysRoleCollege> implements SysRoleCollegeService {

}
