package run.app.step.project.system.mapper;

import org.springframework.stereotype.Repository;
import run.app.step.project.system.entity.SysLogininfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author lingSong
 * @since 2020-09-27
 */
@Repository
public interface SysLogininfoMapper extends BaseMapper<SysLogininfo> {

    /**
     * 插入
     *
     * @param logininfor
     * @return
     */
    int insertLogininfor(SysLogininfo logininfor);
}
