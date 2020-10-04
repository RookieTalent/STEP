package run.app.step.project.system.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import run.app.step.project.system.entity.SysCourse;
import run.app.step.project.system.mapper.SysCourseMapper;
import run.app.step.project.system.service.SysCourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author lingSong
 * @since 2020-09-16
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class SysCourseServiceImpl extends ServiceImpl<SysCourseMapper, SysCourse> implements SysCourseService {

    @Autowired
    private SysCourseMapper courseMapper;


}
