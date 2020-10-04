package run.app.step.common.utils.beans;

import org.springframework.beans.BeanWrapperImpl;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;
import run.app.step.common.exception.NotFoundException;

import java.beans.PropertyDescriptor;
import java.util.HashSet;
import java.util.Set;

/**
 * @author lingSong
 * @date 2020/8/16 16:49
 */
public class BeanUtils {
    private BeanUtils(){
    }


    /**
     * 获取转换对象的实例
     * @param source
     * @param targetClass
     * @param <T>
     * @return
     */
    @Nullable
    public static <T> T transformFrom(@Nullable Object source, @NonNull Class<T> targetClass) {
        Assert.notNull(targetClass, "Target class must not be null");

        if (source == null) {
            return null;
        }

        try{
            T targetInstance = targetClass.newInstance();
            org.springframework.beans.BeanUtils.copyProperties(source, targetInstance, getNullPropertyNames(source));
            //return
            return targetInstance;
        }catch (Exception e){
            //TODO 改善异常抛出类
            throw new NotFoundException("the source not exists");
        }

    }

    @NonNull
    private static String[] getNullPropertyNames(@NonNull Object source) {
        //节省空间同时获取返回值为String[]的数组
        return getNullPropertyNameSet(source).toArray(new String[0]);
    }

    @NonNull
    private static Set<String> getNullPropertyNameSet(@NonNull Object source) {
        Assert.notNull(source, "source object must not be null");

        //spring中的bean都是被wrapper封装的 如果没记错的话 所以可以通过它获取属性描述
        BeanWrapperImpl beanWrapper = new BeanWrapperImpl(source);
        PropertyDescriptor[] propertyDescriptors = beanWrapper.getPropertyDescriptors();

        Set<String> emptyNames = new HashSet<>();

        for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
            String name = propertyDescriptor.getName();
            Object value = beanWrapper.getPropertyValue(name);

            if(value == null){
                emptyNames.add(name);
            }
        }

        return emptyNames;
    }

}
