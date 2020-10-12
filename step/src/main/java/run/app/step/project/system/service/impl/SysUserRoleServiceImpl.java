package run.app.step.project.system.service.impl;

import org.springframework.transaction.annotation.Transactional;
import run.app.step.common.exception.DBException;
import run.app.step.project.system.entity.SysUserRole;
import run.app.step.project.system.mapper.SysUserRoleMapper;
import run.app.step.project.system.service.SysUserRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户和角色关联表 服务实现类
 * </p>
 *
 * @author lingSong
 * @since 2020-10-06
 */
@Service
@Transactional(rollbackFor = DBException.class)
public class SysUserRoleServiceImpl extends ServiceImpl<SysUserRoleMapper, SysUserRole> implements SysUserRoleService {

}
