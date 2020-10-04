package run.app.step.project.system.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import run.app.step.project.system.entity.SysChapter;
import run.app.step.project.system.mapper.SysChapterMapper;
import run.app.step.project.system.service.SysChapterService;
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
public class SysChapterServiceImpl extends ServiceImpl<SysChapterMapper, SysChapter> implements SysChapterService {

    @Autowired
    private SysChapterMapper chapterMapper;

}
