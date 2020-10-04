package run.app.step.project.system.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import run.app.step.project.system.entity.SysDictData;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import run.app.step.project.system.entity.param.system.dict.DictDataQuery;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author lingSong
 * @since 2020-09-22
 */
@Repository
public interface SysDictDataMapper extends BaseMapper<SysDictData> {

    /**
     * 根据条件分页查询字典数据
     *
     * @param dataQuery
     * @return
     */
    List<SysDictData> selectDictDataList(DictDataQuery dataQuery);

    /**
     * 根据字典数据ID查询信息
     *
     * @param dictCode
     * @return
     */
    SysDictData selectDictDataById(Long dictCode);

    /**
     * 批量删除数字字典数据
     *
     * @param dictCodes
     * @return
     */
    int deleteDictDataByIds(Long[] dictCodes);

    /**
     * 查询字典数据数量
     *
     * @param dictType
     * @return
     */
    int countDictDataByType(String dictType);

    /**
     * 同步修改dict_data表
     *
     * @param oldDictType
     * @param newDictType
     */
    void updateDictDataType(@Param("oldDictType") String oldDictType,@Param("newDictType") String newDictType);

    /**
     * 根据字典类型查询
     *
     * @param dictType
     * @return
     */
    List<SysDictData> selectDictDataByType(String dictType);
}
