package run.app.step.project.system.service;

import org.springframework.lang.NonNull;
import run.app.step.project.system.entity.SysUser;
import com.baomidou.mybatisplus.extension.service.IService;
import run.app.step.project.system.entity.param.LoginParam;
import run.app.step.project.system.entity.param.system.user.UserQuery;
import run.app.step.project.system.entity.vo.TokenVO;
import run.app.step.project.system.entity.vo.TreeSelect;
import run.app.step.project.system.entity.vo.system.user.UserVO;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author lingSong
 * @since 2020-08-14
 */
public interface SysUserService extends IService<SysUser> {


    /**
     * 获取用户列表
     * @param userQuery
     * @return
     */
    List<UserVO> selectUserList(UserQuery userQuery);

    /**
     * 新增用户
     * @param newUser
     */
    int insertUser(SysUser newUser);

    /**
     * 通过手机号查询
     * 为空抛出异常
     * @param mobile
     * @return
     */
    SysUser findByMobileOfNonNull(@NonNull String mobile);

    /**
     * 通过邮箱查询
     * 为空抛出异常
     * @param email
     * @return
     */
    SysUser findByEmailOfNonNull(@NonNull String email);

    /**
     * 校验用户昵称的唯一性
     * @param nickname
     * @return
     */
    int checkNickNameUnique(String nickname);

    /**
     * 校验手机号的唯一性
     * @param mobile
     * @return
     */
    int checkMobileUnique(String mobile);

    /**
     * 校验用户邮箱的唯一性
     * @param email
     * @return
     */
    int checkEmailUnique(String email);

    /**
     * 根据用户id查询用户信息
     * TODO 这个写的有点拉胯  等看到mybatis底层后看看能不能优化 写sql语句后 注解会失效  目前我还不懂原因
     * @param id
     * @return
     */
    UserVO selectUserById(String id);

    /**
     * 根据用户昵称查询用户信息
     *
     * @param nickname
     * @return
     */
    SysUser selectUserByNickName(String nickname);

    /**
     * 更新用户信息
     * @param sysUser
     */
    int updateUser(SysUser sysUser);

    /**
     * 删除用户信息, 可批量
     * @param ids
     */
    int deleteUserByIds(String[] ids, String code, String mobile);

    /**
     * 导入用户数据
     *
     * @param userList
     * @return
     */
    String importUser(List<SysUser> userList, boolean updateSupport);

    /**
     * 查询用户选择树
     *
     * @return
     */
    List<TreeSelect> buildUserTreeSelect(List<UserVO> list);

    /**
     * 用户登出就扣
     * @param accessToken
     * @param refreshToken
     */
    void logout(String accessToken, String refreshToken);

}
