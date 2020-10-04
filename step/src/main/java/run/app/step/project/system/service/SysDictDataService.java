package run.app.step.project.system.service;

import run.app.step.project.system.entity.SysDictData;
import com.baomidou.mybatisplus.extension.service.IService;
import run.app.step.project.system.entity.param.system.dict.DictDataQuery;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author lingSong
 * @since 2020-09-22
 */
public interface SysDictDataService extends IService<SysDictData> {

    /**
     * 新增保存字典数据信息
     *
     * @param dictData
     * @return
     */
    void insertDictData(SysDictData dictData);

    /**
     *根据条件分页查询字典数据
     *
     * @param dataQuery
     * @return
     */
    List<SysDictData> selectDictDataList(DictDataQuery dataQuery);

    /**
     *  根据字典数据ID查询信息
     *
     * @param dictCode
     * @return
     */
    SysDictData selectDictDataById(Long dictCode);

    /**
     * 修改字典数据信息
     *
     * @param dictData
     */
    void updateDictData(SysDictData dictData);

    /**
     * 删除字典类型
     *
     * @param dictCodes
     */
    void deleteDictDataByIds(Long[] dictCodes);
}
