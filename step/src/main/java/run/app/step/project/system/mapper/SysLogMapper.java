package run.app.step.project.system.mapper;

import org.springframework.stereotype.Repository;
import run.app.step.project.system.entity.SysLog;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 系统日志 Mapper 接口
 * </p>
 *
 * @author lingSong
 * @since 2020-10-09
 */
@Repository
public interface SysLogMapper extends BaseMapper<SysLog> {

    /**
     * 根据条件查询列表信息
     *
     * @param convertTo
     * @return
     */
    List<SysLog> selectOperLogList(SysLog convertTo);

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
