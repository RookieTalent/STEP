package run.app.step.framework.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import run.app.step.common.constants.Constants;
import run.app.step.common.utils.ServiceUtil;
import run.app.step.common.utils.StringUtils;
import run.app.step.framework.cache.RedisService;
import run.app.step.framework.security.LoginUser;
import run.app.step.framework.security.utils.JwtTokenUtil;

import java.util.Set;

/**
 * @author lingSong
 * @date 2020/9/29 9:20
 */
@Service("ss")
public class PermissionService {

    /** 所有权限标识 */
    private static final String ALL_PERMISSION = "*:*:*";

    /** 管理员角色权限标识 */
    private static final String SUPER_ADMIN = "admin";

    private static final String ROLE_DELIMETER = ",";

    private static final String PERMISSION_DELIMETER = ",";

    @Autowired
    private RedisService redisService;

    /**
     * 验证用户是否具备某些权限
     *
     * @param permission
     * @return
     */
    public boolean hasPermi(String permission){
        if(StringUtils.isEmpty(permission)){
            return false;
        }

        LoginUser loginUser = getLoginUser();
        if(StringUtils.isNull(loginUser) || CollectionUtils.isEmpty(loginUser.getPermissions())){
            return false;
        }

        return hasPermissions(loginUser.getPermissions(), permission);
    }

    /**
     * 检验用户是否不具备某权限
     *
     * @param permission
     * @return
     */
    public boolean lackPermi(String permission){
        return hasPermi(permission) != true;
    }

    /**
     * 验证用户是否具备以下任意一个权限
     *
     * @param permissions
     * @return
     */
    public boolean hasAnyPermi(String permissions){
       if(StringUtils.isEmpty(permissions)){
           return false;
       }

        LoginUser loginUser = getLoginUser();
       if(StringUtils.isNull(loginUser) || CollectionUtils.isEmpty(loginUser.getPermissions())){
           return false;
       }
       Set<String> authorities = loginUser.getPermissions();
        for (String permission : permissions.split(PERMISSION_DELIMETER)) {
            if(permission != null && hasPermissions(authorities, permission)){
                return true;
            }
        }

        return false;
    }


    private boolean hasPermissions(Set<String> permissions, String permission){
        return permission.contains(ALL_PERMISSION) || permissions.contains(StringUtils.trim(permission));
    }


    private LoginUser getLoginUser(){
        String userKey = JwtTokenUtil.obtainLoginUser(ServiceUtil.getRequest().getHeader(Constants.ACCESS_TOKEN));
        return (LoginUser) redisService.get(userKey);
    }


}
