package run.app.step.framework.factory.handler;

import cn.hutool.core.convert.Convert;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.DateUtil;
import org.springframework.stereotype.Component;
import run.app.step.common.constants.Constants;
import run.app.step.common.utils.DateUtils;
import run.app.step.framework.factory.NoIfFactory;

import java.util.Date;

/**
 * 把refrelection.convert提到此处
 *
 * @author lingSong
 * @date 2020/9/1 20:57
 */
@Component
public class ReflectionNoIfHandler extends AbstractNoIfHandler {

    @Override
    public void ReflectConvertNoif(Class<?>[] cs, Object[] args) {
        for (int i = 0; i < cs.length; i++){
            if(args[i] != null && !args[i].getClass().equals(cs[i])){
                if(cs[i] == String.class){
                    args[i] = Convert.toStr(args[i]);
                    if (StringUtils.endsWith((String) args[i], ".0")){
                        args[i] = StringUtils.substringBefore((String) args[i], ".0");
                    }
                }else if(cs[i] == Integer.class){
                    args[i] = Convert.toInt(args[i]);
                }else if(cs[i] == Long.class){
                    args[i] = Convert.toLong(args[i]);
                }else if (cs[i] == Double.class){
                    args[i] = Convert.toDouble(args[i]);
                }
                else if (cs[i] == Float.class){
                    args[i] = Convert.toFloat(args[i]);
                }
                else if (cs[i] == Date.class){
                    if (args[i] instanceof String){
                        args[i] = DateUtils.parseDate(args[i]);
                    }else{
                        args[i] = DateUtil.getJavaDate((Double) args[i]);
                    }
                }
            }
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        NoIfFactory.register(Constants.REFLECTION_NO_IF, this);
    }
}
