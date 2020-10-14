package run.app.step.project.system.mapper;

import org.springframework.stereotype.Repository;
import run.app.step.project.system.entity.SysNotice;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import run.app.step.project.system.entity.param.system.notice.NoticeQuery;

import java.util.List;

/**
 * <p>
 * 通知公告表 Mapper 接口
 * </p>
 *
 * @author lingSong
 * @since 2020-10-12
 */
@Repository
public interface SysNoticeMapper extends BaseMapper<SysNotice> {

    /**
     * 查询公告列表
     *
     * @param query
     * @return
     */
    List<SysNotice> selectNoticeList(NoticeQuery query);

    /**
     * 根据编号获取详细信息
     *
     * @param id
     * @return
     */
    SysNotice selectNoticeById(Long id);

    /**
     * 批量删除公告信息
     *
     * @param noticeIds
     * @return
     */
    int deleteNoticeByIds(Long[] noticeIds);

    /**
     * 首页播报公告
     *
     * @return
     */
    List<SysNotice> playNotice();

}
