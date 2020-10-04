package run.app.step.college;

import org.junit.jupiter.api.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import run.app.step.project.system.entity.SysCollege;
import run.app.step.project.system.entity.SysUserCollege;
import run.app.step.project.system.entity.param.system.college.CollegeQuery;
import run.app.step.project.system.mapper.SysCollegeMapper;
import run.app.step.project.system.mapper.SysUserCollegeMapper;
import run.app.step.project.system.service.SysCollegeService;

import java.util.List;
import java.util.Objects;

/**
 * @author lingSong
 * @date 2020/9/7 15:36
 */
@SpringBootTest
public class insert {

    @Autowired
    private SysCollegeService collegeService;

    @Autowired
    private SysCollegeMapper collegeMapper;

    @Autowired
    private SysUserCollegeMapper userCollegeMapper;

    @Test
    public void test1(){
        SysCollege sysCollege = new SysCollege();

        sysCollege.setParentId("0");
        sysCollege.setCollegeName("旅游管理");
        sysCollege.setLeader("小林");
        sysCollege.setMobile("13695740292");
        sysCollege.setEmail("2544472820@qq.com");


        collegeMapper.insert(sysCollege);
    }

    @Test
    public void test2(){
        SysCollege college = new SysCollege();
        college.setCollegeName("移动通信技术");
        String result = collegeService.checkCollegeNameUnique(college);

        System.out.println(result);
    }


    @Test
    public void test3(){
        String userId = "1298508116144488450";
        String collegeId = "1304046577828032513";

        SysUserCollege userCollege = userCollegeMapper.selectByInfo(collegeId, userId);

        System.out.println("userCollege = " + userCollege);

    }

    @Test
    public void test4(){
        int i = collegeMapper.checkDeptExistUser("1302882309384454146");
        System.out.println(i);
    }
}
