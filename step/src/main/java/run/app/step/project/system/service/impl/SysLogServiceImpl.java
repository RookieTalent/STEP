package run.app.step.project.system.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import run.app.step.common.exception.DBException;
import run.app.step.project.system.entity.SysLog;
import run.app.step.project.system.entity.param.system.log.OperLogQuery;
import run.app.step.project.system.mapper.SysLogMapper;
import run.app.step.project.system.service.SysLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 系统日志 服务实现类
 * </p>
 *
 * @author lingSong
 * @since 2020-10-09
 */
@Service
@Transactional(rollbackFor = DBException.class)
public class SysLogServiceImpl extends ServiceImpl<SysLogMapper, SysLog> implements SysLogService {

    @Autowired
    private SysLogMapper logMapper;

    @Override
    public List<SysLog> selectOperLogList(OperLogQuery query) {
        return logMapper.selectOperLogList(query.convertTo());
    }

    @Override
    public void deleteOperLogByIds(Long[] operIds) {
        logMapper.deleteOperLogByIds(operIds);
    }

    @Override
    public void cleanOperLog() {
        logMapper.cleanOperLog();
    }
}
