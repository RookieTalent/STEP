package run.app.step.project.system.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import run.app.step.common.utils.ServiceUtil;
import run.app.step.common.utils.dict.DictUtils;
import run.app.step.project.system.entity.SysDictData;
import run.app.step.project.system.entity.param.system.dict.DictDataQuery;
import run.app.step.project.system.mapper.SysDictDataMapper;
import run.app.step.project.system.service.SysDictDataService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author lingSong
 * @since 2020-09-22
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class SysDictDataServiceImpl extends ServiceImpl<SysDictDataMapper, SysDictData> implements SysDictDataService {

    @Autowired
    private SysDictDataMapper dictDataMapper;

    @Override
    public void insertDictData(SysDictData dictData) {
        int row = dictDataMapper.insert(dictData);
        ServiceUtil.throwsException(row, "插入字典数据失败. 请稍后重新操作");
        if(row > 0) DictUtils.clearDictCache();
    }

    @Override
    public List<SysDictData> selectDictDataList(DictDataQuery dataQuery) {
        return dictDataMapper.selectDictDataList(dataQuery);
    }

    @Override
    public SysDictData selectDictDataById(Long dictCode) {
        return dictDataMapper.selectDictDataById(dictCode);
    }

    @Override
    public void updateDictData(SysDictData dictData) {
        int row = dictDataMapper.updateById(dictData);
        ServiceUtil.throwsException(row, "修改字典数据失败， 请稍后重新操作");
        if(row > 0) DictUtils.clearDictCache();
    }

    @Override
    public void deleteDictDataByIds(Long[] dictCodes) {
        int row = dictDataMapper.deleteDictDataByIds(dictCodes);
        ServiceUtil.throwsException(row, "删除字典数据事变， 请稍后重新操作");
        if(row > 0) DictUtils.clearDictCache();
    }


}
