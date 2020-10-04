package run.app.step.project.system.service;

import run.app.step.project.system.entity.SysDictData;
import run.app.step.project.system.entity.SysDictType;
import com.baomidou.mybatisplus.extension.service.IService;
import run.app.step.project.system.entity.param.system.dict.DictTypeQuery;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author lingSong
 * @since 2020-09-22
 */
public interface SysDictTypeService extends IService<SysDictType> {

    /**
     * 查询该字典是否独一无二
     *
     * @param dictType
     * @return
     */
    String checkDictTypeUnique(SysDictType dictType);

    /**
     * 新增保存字典数据信息
     *
     * @param dictType
     * @return
     */
    void insertDictType(SysDictType dictType);

    /**
     * 查询分页数据字典类型类表
     *
     * @param query
     * @return
     */
    List<SysDictType> selectDictTypeList(DictTypeQuery query);

    /**
     * 所有字典类型
     *
     * @return
     */
    List<SysDictType> selectDictTypeAll();

    /**
     * 根据id查询数字字典类型
     *
     * @param id
     * @return
     */
    SysDictType selectDictTypeById(Long id);

    /**
     * 根据字典类型查询字典数据
     *
     * @param dictType
     * @return
     */
    List<SysDictData> selectDictDataByType(String dictType);

    /**
     * 修改数字字典类型信息
     *
     * @param dictType
     */
    void updateDictType(SysDictType dictType);

    /**
     * 批量删除
     *
     * @param dictIds
     */
    int deleteDictTypeByIds(Long[] dictIds);

    /**
     * 清空缓存
     */
    void clearCache();


}
