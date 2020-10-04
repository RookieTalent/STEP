package run.app.step.user;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import run.app.step.common.constants.UserConstants;
import run.app.step.project.system.mapper.SysUserMapper;

/**
 * @author lingSong
 * @date 2020/9/10 16:43
 */
@SpringBootTest
public class check {

    @Autowired
    private SysUserMapper userMapper;

    @Test
    public void  test1(){
        if(UserConstants.UNIQUE.equals(userMapper.checkNickNameUnique("小林"))){
            System.out.println("该用户不存在");
        }else{
            System.out.println("该用户存在");
        }
    }
}
