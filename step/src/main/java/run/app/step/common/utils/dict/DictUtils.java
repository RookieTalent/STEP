package run.app.step.common.utils.dict;

import cn.hutool.core.convert.Convert;
import org.springframework.data.redis.cache.RedisCache;
import run.app.step.common.constants.Constants;
import run.app.step.common.utils.StringUtils;
import run.app.step.common.utils.spring.SpringUtils;
import run.app.step.framework.cache.RedisService;
import run.app.step.project.system.entity.SysDictData;
import run.app.step.project.system.entity.SysDictType;

import java.util.List;
import java.util.Set;

/**
 * @author lingSong
 * @date 2020/9/23 10:11
 */
public class DictUtils {

    /**
     * 设置字典缓存
     *
     * @param key
     * @param dictData
     */
    public static void setDictCache(String key, List<SysDictData> dictData){
        SpringUtils.getBean(RedisService.class).set(getCacheKey(key), dictData);
    }


    /**
     * 清空缓存
     */
    public static void clearDictCache(){
        Set<String> keys = SpringUtils.getBean(RedisService.class).keys(Constants.SYS_DICT_KEY + "*");
        SpringUtils.getBean(RedisService.class).delete(keys);
    }


    /**
     * 设置 cache key
     *
     * @param configKey
     * @return
     */
    public static String getCacheKey(String configKey){
        return Constants.SYS_DICT_KEY+configKey;
    }

    /**
     * 获取字典缓存
     *
     * @param key
     * @return
     */
    public static List<SysDictData> getDictCache(String key) {
        Object cacheObj = SpringUtils.getBean(RedisService.class).get(getCacheKey(key));
        if (StringUtils.isNotNull(cacheObj))
        {
            List<SysDictData> DictDatas = StringUtils.cast(cacheObj);
            return DictDatas;
        }
        return null;
    }
}
