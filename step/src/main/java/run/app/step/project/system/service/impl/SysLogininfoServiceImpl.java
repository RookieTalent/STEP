package run.app.step.project.system.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import run.app.step.common.utils.ServiceUtil;
import run.app.step.project.system.entity.SysLogininfo;
import run.app.step.project.system.mapper.SysLogininfoMapper;
import run.app.step.project.system.service.SysLogininfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author lingSong
 * @since 2020-09-27
 */
@Service
public class SysLogininfoServiceImpl extends ServiceImpl<SysLogininfoMapper, SysLogininfo> implements SysLogininfoService {

    @Autowired
    private SysLogininfoMapper logininfoMapper;

    @Override
    public void insertLogininfor(SysLogininfo logininfor) {
        ServiceUtil.throwsException(logininfoMapper.insertLogininfor(logininfor), "登录日志插入失败");
    }

    @Override
    public List<SysLogininfo> selectLimitLog() {
        return logininfoMapper.selectLimitLog();
    }

}
