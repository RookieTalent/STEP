package run.app.step.common.utils.spring;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.stereotype.Component;

/**
 * @author lingSong
 * @date 2020/9/23 10:09
 */
@Component
public class SpringUtils implements BeanFactoryPostProcessor {

    /** spring引用上下文*/
    private static ConfigurableListableBeanFactory beanFactory;

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {
        SpringUtils.beanFactory = configurableListableBeanFactory;
    }


    /**
     * 获取对象
     *
     * @param name
     * @param <T>
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T> T getBean(String name) throws BeansException{

        return (T) beanFactory.getBean(name);
    }


    /**
     *获取类型为requiredType的对象
     *
     * @param clz
     * @param <T>
     * @return
     * @throws BeansException
     */
    public static <T> T getBean(Class<T> clz) throws BeansException{
        T result = (T) beanFactory.getBean(clz);

        return result;
    }
}
