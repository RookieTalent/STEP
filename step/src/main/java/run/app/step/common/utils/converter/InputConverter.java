package run.app.step.common.utils.converter;

import org.springframework.lang.Nullable;
import run.app.step.common.utils.beans.BeanUtils;
import run.app.step.common.utils.reflection.ReflectionUtils;

import java.lang.reflect.ParameterizedType;
import java.util.Objects;

/**
 *
 * 类型转换接口
 *
 * @author lingSong
 * @date 2020/8/16 16:51
 */
public interface InputConverter<DOMAIN> {

    /**
     * 获取需要转化的实例对象
     * @return
     */
    @SuppressWarnings("unchecked")
    default DOMAIN convertTo() {
        ParameterizedType currentType = parameterizedType();
        // Assert not equal
        Objects.requireNonNull(currentType, "Cannot fetch actual type because parameterized type is null");

        Class<DOMAIN> domainClass = (Class<DOMAIN>) currentType.getActualTypeArguments()[0];

        return BeanUtils.transformFrom(this, domainClass);
    }


    /**
     * 获取参数化类型
     * @return
     */
    @Nullable
    default ParameterizedType parameterizedType(){
        return ReflectionUtils.getParameterizedType(InputConverter.class, this.getClass());
    }
}
