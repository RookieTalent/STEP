package run.app.step.framework.factory;


import com.google.common.collect.Maps;
import run.app.step.common.utils.StringUtils;
import run.app.step.framework.factory.handler.AbstractNoIfHandler;

import java.util.Map;

/**
 * 尝试不在明面上出现if
 * 不用加@Componment 调用静态对象 底层就会触发<clinit>
 *
 * @author lingSong
 * @date 2020/9/1 11:09
 */
public class NoIfFactory {

    private static Map<String, AbstractNoIfHandler> strategyMap = Maps.newHashMap();

    public static AbstractNoIfHandler getInvokeStrategy(String handlerName){
        return strategyMap.get(handlerName);
    }

    /**
     * 往map中添加对应的处理器
     * @param handlerName
     * @param handler
     */
    public static void register(String handlerName, AbstractNoIfHandler handler){
        // 校验
        if(StringUtils.isEmpty(handlerName))    return;

        strategyMap.put(handlerName, handler);
    }
}
