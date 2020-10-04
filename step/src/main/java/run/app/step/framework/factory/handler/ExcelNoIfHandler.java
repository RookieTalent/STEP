package run.app.step.framework.factory.handler;

import cn.hutool.core.convert.Convert;
import org.apache.poi.ss.usermodel.DateUtil;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import run.app.step.common.constants.Constants;
import run.app.step.common.utils.DateUtils;
import run.app.step.common.utils.StringUtils;
import run.app.step.framework.factory.NoIfFactory;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 把excel.convert提到此处
 *
 * @author lingSong
 * @date 2020/9/1 20:56
 */
@Component
public class ExcelNoIfHandler extends AbstractNoIfHandler {

    @Override
    public Object ExcelConvertNoif(Class<?> fieldType, Object val) {
        if(String.class == fieldType){
            String s = Convert.toStr(val);
            if(StringUtils.endsWith(s, ".0")){
                //取 .0 以前的字符串
                val = StringUtils.substringBefore(s, ".0");
            }else{
                val = Convert.toStr(val);
            }
        }else if((Integer.TYPE == fieldType) || (Integer.class == fieldType)){
            val = Convert.toInt(val);
        }else if((Long.TYPE == fieldType) || (Long.class == fieldType)){
            val = Convert.toLong(val);
        }else if((Double.TYPE == fieldType) || (Double.class == fieldType)){
            val = Convert.toDouble(val);
        }else if((Float.TYPE == fieldType) || (Float.class == fieldType)){
            val = Convert.toFloat(val);
        }else if(BigDecimal.class == fieldType){
            val = Convert.toBigDecimal(val);
        }else if(Date.class == fieldType){
            if(val instanceof String){
                val = DateUtils.parseDate(val);
            }else if(val instanceof Double){
                val = DateUtil.getJavaDate((Double) val);
            }
        }

        return val;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        NoIfFactory.register(Constants.EXCEL_NO_IF, this);
    }
}
