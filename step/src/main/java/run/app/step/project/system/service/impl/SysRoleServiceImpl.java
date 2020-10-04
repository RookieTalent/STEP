package run.app.step.project.system.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import run.app.step.common.constants.UserConstants;
import run.app.step.common.utils.StringUtils;
import run.app.step.project.system.entity.SysRole;
import run.app.step.project.system.mapper.SysRoleMapper;
import run.app.step.project.system.service.SysRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * <p>
 * 角色信息表 服务实现类
 * </p>
 *
 * @author lingSong
 * @since 2020-09-29
 */
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {

    @Autowired
    private SysRoleMapper roleMapper;

    @Override
    public String checkRoleNameUnique(SysRole role) {
        SysRole info = roleMapper.checkRoleNameUnique(role.getRoleName());
        if(StringUtils.isNotNull(info)){
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    @Override
    public String checkRoleKeyUnique(SysRole role) {
        SysRole info = roleMapper.checkRoleKeyUnique(role.getRoleKey());
        if(StringUtils.isNotNull(info)){
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    @Override
    public Set<String> selectRolePermissionByUserId(String userId) {
        List<SysRole> perms = roleMapper.selectRolePermissionByUserId(userId);
        Set<String> permsSet = new HashSet<>();
        for (SysRole perm : perms) {
            if(StringUtils.isNotNull(perm)){
                permsSet.addAll(Arrays.asList(perm.getRoleKey().trim().split(",")));
            }
        }
        return permsSet;
    }


}
