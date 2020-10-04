package run.app.step;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.IdUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import run.app.step.common.constants.Constants;
import run.app.step.common.constants.JwtConstants;
import run.app.step.common.constants.UserConstants;
import run.app.step.common.support.AjaxResult;
import run.app.step.common.utils.StringUtils;
import run.app.step.common.utils.VerifyCodeUtils;
import run.app.step.common.utils.dict.DictUtils;
import run.app.step.common.utils.encode.PasswordUtils;
import run.app.step.common.utils.spring.SpringUtils;
import run.app.step.framework.cache.RedisService;
import run.app.step.framework.event.LoginEvent;
import run.app.step.framework.listener.LoginInfoListener;
import run.app.step.framework.security.LoginUser;
import run.app.step.framework.security.service.SysLoginService;
import run.app.step.framework.security.service.SysPermissionService;
import run.app.step.framework.security.utils.JwtTokenUtil;
import run.app.step.project.common.CaptchaController;
import run.app.step.project.system.controller.SysLoginController;
import run.app.step.project.system.entity.*;
import run.app.step.project.system.entity.param.LoginParam;
import run.app.step.project.system.entity.param.system.user.SysUserParam;
import run.app.step.project.system.entity.vo.system.user.UserVO;
import run.app.step.project.system.mapper.SysRoleMenuMapper;
import run.app.step.project.system.mapper.SysUserMapper;
import run.app.step.project.system.service.SysDictTypeService;
import run.app.step.project.system.service.SysUserService;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.*;
import java.util.concurrent.TimeUnit;

@SpringBootTest
class StepApplicationTests {

    @Autowired
    private SysUserMapper userMapper;

    @Autowired
    private SysPermissionService permissionService;

    @Test
    void contextLoads() throws IOException {
        SysUser user = userMapper.selectUserById("1294622577226002433");

        Set<String> menuPermission = permissionService.getMenuPermission(user);
        for (String s : menuPermission) {
            System.out.println("s = " + s);
        }

        Set<String> rolePermssion = permissionService.getRolePermssion(user);
        for (String s : rolePermssion) {
            System.out.println("s = " + s);
        }

    }


    /*
    * sysUserParam.setAge(20);
        sysUserParam.setAvatar(null);
        sysUserParam.setEmail("17730088986@163.com");
        sysUserParam.setMobile("17730088986");
        sysUserParam.setNickname("cherry");
        sysUserParam.setSex(1);
        sysUserParam.setPassword("admin");

        SysUser sysUser = sysUserParam.convertTo();
        System.out.println("sysUser = " + sysUser);

        int result = userService.insertUser(sysUser);
        System.out.println("result = " + result);
    *
    * */


}
