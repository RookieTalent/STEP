package run.app.step.convert;

import lombok.Data;
import run.app.step.common.utils.converter.InputConverter;


/**
 * @author lingSong
 * @date 2020/8/16 20:15
 */
@Data
public class postParam implements InputConverter<post>{

    private String param;

    private String name;

    public static void main(String[] args) {
        /*postParam postParam = new postParam();

        postParam.setName("111");
        postParam.setParam(null);

        postParam postParam2 = new postParam();

        BeanUtils.copyProperties(postParam, postParam2);
        System.out.println(postParam2);*/
        /*Type[] genericInterfaces = postParam.getClass().getGenericInterfaces();
        System.out.println(Arrays.asList(genericInterfaces));
        for (Type genericInterface : genericInterfaces) {
            if(genericInterface instanceof ParameterizedType){
                ParameterizedType parameterizedType = (ParameterizedType)genericInterface;
                System.out.println(parameterizedType);
            }
        }*/



    }
}
