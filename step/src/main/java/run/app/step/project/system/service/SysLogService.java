package run.app.step.project.system.service;

import run.app.step.project.system.entity.SysLog;
import com.baomidou.mybatisplus.extension.service.IService;
import run.app.step.project.system.entity.param.system.log.OperLogQuery;

import java.util.List;

/**
 * <p>
 * 系统日志 服务类
 * </p>
 *
 * @author lingSong
 * @since 2020-10-09
 */
public interface SysLogService extends IService<SysLog> {

    /**
     * 查询分页列表
     *
     * @param query
     * @return
     */
    List<SysLog> selectOperLogList(OperLogQuery query);

    /**
     * 批量删除日志
     *
     * @param operIds
     */
    void deleteOperLogByIds(Long[] operIds);

    /**
     * 清空日志表
     */
    void cleanOperLog();

}
