package run.app.step.project.system.service;

import run.app.step.project.system.entity.SysNotice;
import com.baomidou.mybatisplus.extension.service.IService;
import run.app.step.project.system.entity.param.system.notice.NoticeQuery;

import java.util.List;

/**
 * <p>
 * 通知公告表 服务类
 * </p>
 *
 * @author lingSong
 * @since 2020-10-12
 */
public interface SysNoticeService extends IService<SysNotice> {

    /**
     * 查询公告列表
     *
     * @param query
     * @return
     */
    List<SysNotice> selectNoticeList(NoticeQuery query);

    /**
     * 新增公告通知
     *
     * @param notice
     */
    void insertNotice(SysNotice notice);

    /**
     * 修改公告通知
     *
     * @param notice
     */
    void updateNotice(SysNotice notice);

    /**
     *
     * 根据编号获取详细信息
     *
     * @param id
     * @return
     */
    SysNotice selectNoticeById(Long id);

    /**
     * 可批量删除
     *
     * @param noticeIds
     */
    void deleteNoticeByIds(Long[] noticeIds);

    /**
     * 播放通知
     *
     * @return
     */
    List<SysNotice> playNotice();

}
