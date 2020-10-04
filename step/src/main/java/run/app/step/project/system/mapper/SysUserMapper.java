package run.app.step.project.system.mapper;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;
import run.app.step.project.system.entity.SysUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import run.app.step.project.system.entity.param.system.user.UserQuery;
import run.app.step.project.system.entity.vo.system.user.UserVO;

import java.util.List;


/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author lingSong
 * @since 2020-08-14
 */
@Repository
public interface SysUserMapper extends BaseMapper<SysUser> {

    /**
     * 查询是否存在该手机号
     * @param mobile
     * @return
     */
    @NonNull
    SysUser findByMobile(@NonNull String mobile);

    /**
     * 查询是否存在该邮箱
     * @param email
     * @return
     */
    @NonNull
    SysUser findByEmail(@NonNull String email);

    /**
     * 查询是否存在该昵称
     * @param nickName
     * @return
     */
    @NonNull
    SysUser findByNickName(@NonNull String nickName);

    /**
     * 根据条件查询用户列表
     * @param userQuery
     * @return
     */
    @NonNull
    List<UserVO> selectUserList(UserQuery userQuery);

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
     * @param id
     * @return
     */
    SysUser selectUserById(String id);

    /**
     * 删除用户信息 可批量 逻辑删除
     * @param ids
     * @return
     */
    int deleteUserByIds(String[] ids);

    /**
     * 根据用户昵称查询用户信息
     *
     * @param nickname
     * @return
     */
    SysUser selectUserByNickName(String nickname);
}
