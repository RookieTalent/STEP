package run.app.step.framework.aspectj.lang.annotation;

import run.app.step.common.enums.code.BusinessType;

import java.lang.annotation.*;

/**
 * @author lingSong
 * @date 2020/10/9 21:39
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface AutoLog {

    /**
     * 模块
     */
    String title() default "";

    /**
     * 实现的功能
     */
    String action() default "";

    /**
     * 方法方式
     * @return
     */
    BusinessType businessType() default BusinessType.OTHER;
}
