package run.app.step.framework.factory.handler;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.core.Authentication;
import run.app.step.project.system.entity.SysCollege;
import run.app.step.project.system.entity.SysMenu;
import run.app.step.project.system.entity.SysUser;
import run.app.step.project.system.entity.param.LoginParam;
import run.app.step.project.system.entity.param.system.user.SysUserParam;

/**
 * 尝试模板模式
 * (总感觉自己把简单的玩意太抽象化了)
 *
 * @author lingSong
 * @date 2020/9/1 20:47
 */
public abstract class AbstractNoIfHandler implements InitializingBean {

    /**
     * 登录认证处
     */
    public Authentication authNoif(String captcha, String code, SysUser userInfo, LoginParam loginParam){
        throw new UnsupportedOperationException();
    }

    /**
     * 用户新增处
     */
    public void insertUserNoif(SysUser newUser){
        throw new UnsupportedOperationException();
    }

    /**
     * 用户更新处
     */
    public void updateUserNoif(SysUser sysUser){
        throw new UnsupportedOperationException();
    }

    /**
     * 学院删除处
     */
    public void deleteCollegeNoif(String id){
        throw new UnsupportedOperationException();
    }

    /**
     * 菜单增加或修改处
     */
    public void insertOrUpdateMenuNoif(SysMenu menu){
        throw new UnsupportedOperationException();
    }

    /**
     * 学院更新处
     */
    public void updateCollegeNoif(SysCollege college){
        throw new UnsupportedOperationException();
    }

    /**
     * excel 类型匹配处
     */
    public Object ExcelConvertNoif(Class<?> fieldType, Object val){
        throw new UnsupportedOperationException();
    }

    /**
     * reflect setter处
     */
    public void ReflectConvertNoif(Class<?>[] cs, Object[] args){
        throw new UnsupportedOperationException();
    }

}
