package run.app.step.common.utils.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 转换类型工具
 *
 * @author lingSong
 * @date 2020/10/12 15:01
 */
public class ConvertUtils {

    private static final ObjectMapper MAPPER = new ObjectMapper();


    /**
     * 将对象转换成json字符串。
     * <p>Title: pojoToJson</p>
     * <p>Description: </p>
     * @param data
     * @return
     */
    public static String objectToJson(Object data) {
        try {
            String string = MAPPER.writeValueAsString(data);
            return string;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 把json结果集转为对象
     *
     * @param jsonData
     * @param beanType
     * @param <T>
     * @return
     */
    public static <T> T jsonToPojo(String jsonData, Class<T> beanType){
        try {
            T t = MAPPER.readValue(jsonData, beanType);
            return t;
        }catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }
}
