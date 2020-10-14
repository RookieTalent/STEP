package run.app.step.project.system.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import run.app.step.common.exception.DBException;
import run.app.step.common.utils.ServiceUtil;
import run.app.step.project.system.entity.SysNotice;
import run.app.step.project.system.entity.param.system.notice.NoticeQuery;
import run.app.step.project.system.mapper.SysNoticeMapper;
import run.app.step.project.system.service.SysNoticeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * <p>
 * 通知公告表 服务实现类
 * </p>
 *
 * @author lingSong
 * @since 2020-10-12
 */
@Service
@Transactional(rollbackFor = DBException.class)
public class SysNoticeServiceImpl extends ServiceImpl<SysNoticeMapper, SysNotice> implements SysNoticeService {

    @Autowired
    private SysNoticeMapper noticeMapper;

    @Override
    public List<SysNotice> selectNoticeList(NoticeQuery query) {
        return noticeMapper.selectNoticeList(query);
    }

    @Override
    public void insertNotice(SysNotice notice) {
        ServiceUtil.throwsException(noticeMapper.insert(notice), "新增公告通知失败");
    }

    @Override
    public void updateNotice(SysNotice notice) {
        ServiceUtil.throwsException(noticeMapper.updateById(notice), "修改公告通知失败");
    }

    @Override
    public SysNotice selectNoticeById(Long id) {
        return noticeMapper.selectNoticeById(id);
    }

    @Override
    public void deleteNoticeByIds(Long[] noticeIds) {
        int result = noticeMapper.deleteNoticeByIds(noticeIds);
    }

    @Override
    public List<SysNotice> playNotice() {
        return noticeMapper.playNotice();
    }


}
