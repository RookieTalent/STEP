package run.app.step.project.system.service;

import run.app.step.project.system.entity.SysLogininfo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author lingSong
 * @since 2020-09-27
 */
public interface SysLogininfoService extends IService<SysLogininfo> {

    /**
     * 新增系统登录日志
     *
     * @param loginin 访问日志对象
     */
    public void insertLogininfor(SysLogininfo loginin);

}
