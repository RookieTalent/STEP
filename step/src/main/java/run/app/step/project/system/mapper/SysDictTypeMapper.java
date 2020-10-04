package run.app.step.project.system.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import run.app.step.project.system.entity.SysDictType;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import run.app.step.project.system.entity.param.system.dict.DictTypeQuery;

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
public interface SysDictTypeMapper extends BaseMapper<SysDictType> {

    /**
     * 检测字典类型名称是否唯一
     *
     * @param dictType
     * @return
     */
    SysDictType checkDictTypeUnique(String dictType);

    /**
     * 根据条件分页查询字典类型
     *
     * @param query
     * @return
     */
    List<SysDictType> selectDictTypeList(DictTypeQuery query);

    /**
     * 根据id查询数字字典类型
     *
     * @param id
     * @return
     */
    SysDictType selectDictTypeById(Long id);

    /**
     * 批量删除字典类型信息
     *
     * @param dictIds
     * @return
     */
    int deleteDictTypeByIds(Long[] dictIds);
}
