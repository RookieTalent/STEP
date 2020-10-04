package run.app.step.project.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import run.app.step.common.constants.UserConstants;
import run.app.step.common.exception.AlreadyExistsException;
import run.app.step.common.exception.BadRequestException;
import run.app.step.common.utils.ServiceUtil;
import run.app.step.common.utils.StringUtils;
import run.app.step.common.utils.dict.DictUtils;
import run.app.step.framework.aspectj.lang.annotation.Excel;
import run.app.step.project.system.entity.SysDictData;
import run.app.step.project.system.entity.SysDictType;
import run.app.step.project.system.entity.param.system.dict.DictTypeQuery;
import run.app.step.project.system.mapper.SysDictDataMapper;
import run.app.step.project.system.mapper.SysDictTypeMapper;
import run.app.step.project.system.service.SysDictTypeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
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
public class SysDictTypeServiceImpl extends ServiceImpl<SysDictTypeMapper, SysDictType> implements SysDictTypeService {

    @Autowired
    private SysDictTypeMapper dictTypeMapper;

    @Autowired
    private SysDictDataMapper dictDataMapper;

    /**
     * 项目启动时，初始化字典到缓存
     */
    @PostConstruct
    public void init(){
        List<SysDictType> dictTypeList = selectDictTypeAll();
        for (SysDictType dictType : dictTypeList) {
            List<SysDictData> dictDatas = dictDataMapper.selectDictDataByType(dictType.getDictType());
            DictUtils.setDictCache(dictType.getDictType(), dictDatas);
        }
    }

    @Override
    public String checkDictTypeUnique(SysDictType dictType) {
        SysDictType dict = dictTypeMapper.checkDictTypeUnique(dictType.getDictType());
        if(StringUtils.isNotNull(dict)){
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    @Override
    public void insertDictType(SysDictType dictType) {
        if(UserConstants.NOT_UNIQUE.equals(checkDictTypeUnique(dictType))){
            throw new AlreadyExistsException("该字典类型已经存在，请重新输入");
        }
        int row = dictTypeMapper.insert(dictType);
        ServiceUtil.throwsException(row, "数据字典类型插入失败，请稍微重新操作");
        if(row > 0) DictUtils.clearDictCache();
    }

    @Override
    public List<SysDictType> selectDictTypeList(DictTypeQuery query) {
        return dictTypeMapper.selectDictTypeList(query);
    }

    @Override
    public List<SysDictType> selectDictTypeAll() {
        return dictTypeMapper.selectList(new QueryWrapper<>());
    }

    @Override
    public SysDictType selectDictTypeById(Long id) {
        return dictTypeMapper.selectDictTypeById(id);
    }

    @Override
    public List<SysDictData> selectDictDataByType(String dictType) {
        List<SysDictData> dictDatas = DictUtils.getDictCache(dictType);
        if(StringUtils.isNotNull(dictDatas)){
            return dictDatas;
        }
        dictDatas = dictDataMapper.selectDictDataByType(dictType);
        if(StringUtils.isNotNull(dictDatas)){
            return dictDatas;
        }
        return null;
    }

    @Override
    public void updateDictType(SysDictType dictType) {
        // 校验
        if(UserConstants.NOT_UNIQUE.equals(checkDictTypeUnique(dictType))){
            throw new AlreadyExistsException("修改字典'" + dictType.getDictName() + "'失败， 字典类型已经存在");
        }

        SysDictType oldDict = dictTypeMapper.selectDictTypeById(dictType.getId());
        // 同步修改sys_dict_data表
        dictDataMapper.updateDictDataType(oldDict.getDictType(), dictType.getDictType());
        int row = dictTypeMapper.updateById(dictType);
        ServiceUtil.throwsException(row, "修改数字字典类型失败， 请稍后重新操作");
        if(row > 0) DictUtils.clearDictCache();
    }

    @Override
    public int deleteDictTypeByIds(Long[] dictIds) {
        for (Long dictId : dictIds) {
            SysDictType dictType = selectDictTypeById(dictId);
            if(dictDataMapper.countDictDataByType(dictType.getDictType()) > 0){
                throw new BadRequestException(dictType + "下已分配， 不能删除该字典类型");
            }
        }

        int count = dictTypeMapper.deleteDictTypeByIds(dictIds);
        if(count > 0){
            DictUtils.clearDictCache();
        }
        return count;
    }

    @Override
    public void clearCache() {
        DictUtils.clearDictCache();
    }
}
