package run.app.step.project.system.service.impl;

import cn.hutool.core.lang.Validator;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import run.app.step.common.constants.Constants;
import run.app.step.common.constants.JwtConstants;
import run.app.step.common.constants.UserConstants;
import run.app.step.common.exception.AlreadyExistsException;
import run.app.step.common.exception.BadRequestException;
import run.app.step.common.exception.NotFoundException;
import run.app.step.common.utils.ServiceUtil;
import run.app.step.common.utils.StringUtils;
import run.app.step.common.utils.encode.PasswordUtils;
import run.app.step.framework.cache.RedisService;
import run.app.step.framework.event.LoginEvent;
import run.app.step.framework.factory.NoIfFactory;
import run.app.step.framework.factory.handler.AbstractNoIfHandler;
import run.app.step.framework.security.utils.JwtTokenUtil;
import run.app.step.project.system.entity.SysCollege;
import run.app.step.project.system.entity.SysUser;
import run.app.step.project.system.entity.SysUserCollege;
import run.app.step.project.system.entity.SysUserRole;
import run.app.step.project.system.entity.param.LoginParam;
import run.app.step.project.system.entity.param.system.user.UserQuery;
import run.app.step.project.system.entity.vo.TokenVO;
import run.app.step.project.system.entity.vo.TreeSelect;
import run.app.step.project.system.entity.vo.system.user.UserVO;
import run.app.step.project.system.mapper.SysUserCollegeMapper;
import run.app.step.project.system.mapper.SysUserMapper;
import run.app.step.project.system.mapper.SysUserRoleMapper;
import run.app.step.project.system.service.SysUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;


/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author lingSong
 * @since 2020-08-14
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private RedisService redisService;

    @Autowired
    private SysUserMapper userMapper;

    @Autowired
    private SysUserRoleMapper userRoleMapper;

    @Autowired
    private SysUserCollegeMapper userCollegeMapper;

    @Override
    public List<UserVO> selectUserList(UserQuery userQuery) {
        return userMapper.selectUserList(userQuery);
    }

    @Override
    public void insertUser(SysUser newUser) {
        AbstractNoIfHandler strategy = NoIfFactory.getInvokeStrategy(Constants.USER_NO_IF);
        strategy.insertUserNoif(newUser);

        //设置必要参数
        newUser.setPassword(PasswordUtils.encryptPassword(newUser.getPassword()));
        /*newUser.setPassword(PasswordUtils.encode(newUser.getPassword(), salt));*/

        /** 目前运行结果失败原因是：注解无法被使用int result = userMapper.insertUser(newUser);*/
        ServiceUtil.throwsException(userMapper.insert(newUser), "插入新用户失败");

        //新增用户学院关联
        insertUserCollege(newUser);
        //新增用户与角色关联
        insertUserRole(newUser);
    }



    @Override
    public SysUser findByMobileOfNonNull(String mobile) {
        return Optional.ofNullable(userMapper.findByMobile(mobile)).orElseThrow(
                () -> new NotFoundException("The mobile does not exist").setErrorObject(mobile)
        );
    }

    @Override
    public SysUser findByEmailOfNonNull(String email) {
        return Optional.ofNullable(userMapper.findByEmail(email)).orElseThrow(
                () -> new NotFoundException("The email does not exist").setErrorObject(email)
        );
    }

    @Override
    public int checkNickNameUnique(String nickname) {
        return userMapper.checkNickNameUnique(nickname);
    }

    @Override
    public int checkMobileUnique(String mobile) {
        return userMapper.checkMobileUnique(mobile);
    }

    @Override
    public int checkEmailUnique(String email) {
        return userMapper.checkEmailUnique(email);
    }

    @Override
    public UserVO selectUserById(String id) {
        return convertToVO(id);
    }

    @Override
    public SysUser selectUserByNickName(String nickname) {
        return userMapper.selectUserByNickName(nickname);
    }

    @Override
    public int updateUser(SysUser sysUser) {
        final String userId = sysUser.getId();

        AbstractNoIfHandler strategy = NoIfFactory.getInvokeStrategy(Constants.USER_NO_IF);
        strategy.updateUserNoif(sysUser);

        //删除与用户关联的角色信息
        userRoleMapper.deleteUserRoleByUserId(userId);
        //新增与用户关联的信息
        insertUserRole(sysUser);
        //删除与学院相关联的信息
        userCollegeMapper.deleteUserCollegeByUserId(userId);
        //新增与学院相关联的信息
        insertUserCollege(sysUser);

        return userMapper.updateById(sysUser);
    }

    @Override
    public int deleteUserByIds(String[] ids, String code, String mobile) {
        String sms_captcha = (String) redisService.get(Constants.SMS_CAPTCHA_CODE_KEY + mobile);

        if(sms_captcha == null || !code.equals(sms_captcha)) return 0;

        // 删除用户与角色关联表
        batchDeleteUserRole(ids);
        // 删除用户与学院关联报表
        batchDeleteUserCollege(ids);
        return userMapper.deleteUserByIds(ids);
    }

    @Override
    public String importUser(List<SysUser> userList, boolean updateSupport) {
        if(StringUtils.isNull(userList) || userList.size() == 0){
            throw new NotFoundException("导入用户数据不能为空");
        }

        int successNum = 0;
        int failureNum = 0;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();

        for (SysUser user : userList) {
            try{
                // 验证是否存在该用户
                SysUser u = userMapper.findByMobile(user.getMobile());
                if(StringUtils.isNull(u)){
                    this.insertUser(user);
                    successNum++;
                    successMsg.append("<br/>" + successNum + "、账号 " + user.getNickname() + " 导入成功");
                }else if(updateSupport){
                    this.updateUser(user);
                    successNum++;
                    successMsg.append("<br/>" + successNum + "、账号 " + user.getNickname() + " 更新成功");
                }else{
                    failureNum++;
                    failureMsg.append("<br/>" + failureNum + "、账号 " + user.getNickname() + " 已存在");
                }
            }catch (AlreadyExistsException e){
                failureNum++;
                String msg = "<br/>" + failureNum + "、账号 " + user.getNickname() + " 导入失败：";
                failureMsg.append(msg + e.getMessage());
                log.error(msg, e);
            }
        }

        if(failureNum > 0){
            failureMsg.insert(0, "抱歉，导入失败！ 共" + failureNum + " 条数据格式不正确， 错误如下:");
            throw new AlreadyExistsException(failureMsg.toString());
        }else{
            successMsg.insert(0, "共" + successNum + "已全部成功导入！") ;
        }

        return successMsg.toString();
    }

    @Override
    public List<TreeSelect> buildUserTreeSelect(List<UserVO> list) {
        return list.stream().map(TreeSelect::new).collect(Collectors.toList());
    }

    @Override
    public void logout(String accessToken, String refreshToken) {
        if(StringUtils.isEmpty(accessToken) || StringUtils.isEmpty(refreshToken)){
            log.error("access token or refresh token is empty");
            throw new BadRequestException("token 密匙为空");
        }

        //TODO 引入加密框架获取认证主体 使其登出
        String userId = JwtTokenUtil.obtainUserId(accessToken);
        // 把token加入黑名单 禁止再登录
        redisService.set(Constants.JWT_ACCESS_TOKEN_BLACKLIST+accessToken, userId,
                JwtTokenUtil.getRemainingTime(accessToken), TimeUnit.MILLISECONDS);
        redisService.set(Constants.JWT_REFRESH_TOKEN_BLACKLIST+refreshToken, userId,
                JwtTokenUtil.getRemainingTime(refreshToken), TimeUnit.MILLISECONDS);
    }

    /**
     * 批量删除用户与学院关联表信息
     * @param ids
     */
    private void batchDeleteUserCollege(String[] ids) {
        for (String id : ids) {
            userCollegeMapper.deleteUserCollegeByUserId(id);
        }
    }

    /**
     * 批量删除用户与角色关联表信息
     * @param ids
     */
    private void batchDeleteUserRole(String[] ids) {
        for (String id : ids) {
            userRoleMapper.deleteUserRoleByUserId(id);
        }
    }

    /**
     * 新增用户学院关联
     *
     * @param newUser
     */
    private void insertUserCollege(SysUser newUser) {
        SysCollege college = newUser.getCollege();

        SysUserCollege uc = new SysUserCollege();
        uc.setUserId(newUser.getId());
        uc.setCollegeId(college.getId());

        userCollegeMapper.insert(uc);
    }

    /**
     * 新增用户角色信息
     *
     * @param newUser
     */
    private void insertUserRole(SysUser newUser) {
        Long[] roles = newUser.getRoleIds();
        if(StringUtils.isNotNull(roles)){
            List<SysUserRole> list = new ArrayList<>();
            for (Long roleId : roles) {
                SysUserRole ur = new SysUserRole();
                ur.setUserId(newUser.getId());
                ur.setRoleId(roleId);
                list.add(ur);
            }
            if(list.size() > 0)
                userRoleMapper.batchUserRole(list);
        }
    }


    /**
     * TODO 目前就用于id查询一处 确实不妥
     * @param id
     * @return
     */
    private UserVO convertToVO(String id){
        UserVO userVO = new UserVO();
        SysUser sysUser = userMapper.selectUserById(id);
        BeanUtils.copyProperties(sysUser, userVO);
        return userVO;
    }



}
