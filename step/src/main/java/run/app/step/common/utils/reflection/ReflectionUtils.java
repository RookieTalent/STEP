package run.app.step.common.utils.reflection;

import cn.hutool.core.convert.Convert;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.apache.poi.ss.usermodel.DateUtil;
import org.springframework.lang.NonNull;
import org.springframework.util.Assert;
import run.app.step.common.constants.Constants;
import run.app.step.common.exception.ReflectionException;
import run.app.step.common.utils.DateUtils;
import run.app.step.framework.factory.NoIfFactory;
import run.app.step.framework.factory.handler.AbstractNoIfHandler;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Date;

/**
 * @author lingSong
 * @date 2020/8/16 16:49
 */
@Slf4j
public class ReflectionUtils {

    private static final String SETTER_PREFIX = "set";

    private static final String GETTER_PREFIX = "get";

    private ReflectionUtils(){
    }

    /**
     * 获取参数化类型
     * @param superType
     * @param genericTypes
     * @return
     */
    public static ParameterizedType getParameterizedType(@NonNull Class<?> superType, Type... genericTypes){
        Assert.notNull(superType, "Interface or super type must not be null");

        ParameterizedType currentType = null;

        for (Type genericType : genericTypes) {
            if(genericType instanceof ParameterizedType){
                ParameterizedType parameterizedType = (ParameterizedType)genericType;
                if(parameterizedType.getRawType().getTypeName().equals(superType.getTypeName())){
                    currentType = parameterizedType;
                    break;
                }
            }
        }

        return currentType;
    }


    /**
     * 获取参数化类型
     * @param interfaceType InputConverter
     * @param implementationClass ? extend InputConverter
     * @return
     */
    public static ParameterizedType getParameterizedType(Class<?> interfaceType, Class<?> implementationClass) {
        Assert.notNull(interfaceType, "Interface type must not be null");
        Assert.isTrue(interfaceType.isInterface(), "The give type must be an interface");

        if(implementationClass == null){
            return null;
        }

        ParameterizedType currentType = getParameterizedType(interfaceType, implementationClass.getGenericInterfaces());

        if(currentType != null){
            return currentType;
        }

        Class<?> superclass = implementationClass.getSuperclass();

        return getParameterizedType(interfaceType, superclass);
    }


    /**
     * 调用setter方法，仅匹配方法名
     * 支持多级，如 对象名.对象名.方法 (后期要处理的 sysuser.college.collegeName 之类的)
     * @param obj 实体类
     * @param propertyName 属性名称
     * @param value 属性值
     * @param <E>
     */
    public static <E> void invokeSetter(Object obj, String propertyName, E value){
        Object object = obj;
        String[] names = StringUtils.split(propertyName, ".");
        for (int i = 0; i < names.length; i++) {
            if(i < names.length - 1){
                String getterMethodName = GETTER_PREFIX + StringUtils.capitalize(names[i]);
                object = invokeMethod(object, getterMethodName, new Class[] {}, new Object[] {});
            }else {
                String setterMethodName = SETTER_PREFIX + StringUtils.capitalize(names[i]);
                invokeMethodByName(object, setterMethodName, new Object[] { value });
            }
        }
    }

    /**
     * 直接调用对象方法
     * @param obj
     * @param methodName
     * @param parameterTypes
     * @param args
     * @param <E>
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <E> E invokeMethod(final Object obj, final String methodName, final Class<?>[] parameterTypes,
                                     final Object[] args){
        if(obj == null || methodName == null) return null;

        Method method = getAccessibleMethod(obj, methodName, parameterTypes);
        if(method == null){
            log.debug("在 [" + obj.getClass() + "] 中，没有找到 [" + methodName + "] 方法 ");
            return null;
        }

        try {
            return (E) method.invoke(obj, args);
        }catch (Exception e){
            String msg = "method: " + method + ", obj:" + obj + ", args:" + args + "";
            log.debug("method: [" + method + "], obj:[" + obj + "], args:[" + args + "] is error");
            throw new ReflectionException(msg);
        }
    }
    /**
     * 直接调用对象方法
     * 只匹配函数名，匹配到多个 匹配第一个
     * @param obj
     * @param methodName
     * @param args
     * @param <E>
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <E> E invokeMethodByName(final Object obj, final String methodName, final Object[] args){

        Method method = getAccessibleMethodByName(obj, methodName, args.length);
        if(method == null){
            log.debug("在 [" + obj.getClass() + "] 中没有找到 [" + methodName + "] 方法" );
            return null;
        }

        try{
            // 类型转换 （将参数数据类型转换为目标方法参数类型）
            Class<?>[] cs = method.getParameterTypes();
            AbstractNoIfHandler strategy = NoIfFactory.getInvokeStrategy(Constants.REFLECTION_NO_IF);
            strategy.ReflectConvertNoif(cs, args);
            return (E) method.invoke(obj, args);
        }catch (Exception e){
            String msg = "method: " + method + ", obj:" + obj + ", args:" + args + "";
            log.debug("method: [" + method + "], obj:[" + obj + "], args:[" + args + "] is error");
            throw new ReflectionException(msg);
        }
    }

    /**
     * 循环向上转型, 获取对象的DeclaredMethod,并强制设置为可访问.
     * 匹配函数名+参数类型。
     * @param obj
     * @param methodName
     * @param parameterTypes
     * @return
     */
    public static Method getAccessibleMethod(final Object obj, final String methodName,
                                             final Class<?>... parameterTypes){
        // 为空不报错 直接返回null
        if(obj == null) return null;

        Validate.notBlank(methodName, "methodName can't be blank");
        for (Class<?> searchType = obj.getClass(); searchType != Object.class; searchType = searchType.getSuperclass()){
            try {
                Method method = searchType.getDeclaredMethod(methodName, parameterTypes);
                makeAccessible(method);
                return method;
            }catch (NoSuchMethodException e){
                continue;
            }
        }
        return null;
    }


    /**
     * 循环向上转型（拿全方法）， 获取对象的DeclaredMethod， 并强制设置为可访问
     * 可支持 bean -> basebean
     * @param obj
     * @param methodName
     * @param argsNum
     * @return
     */
    public static Method getAccessibleMethodByName(final Object obj, final String methodName, int argsNum){
        // 为空不报错 直接返回null
        if(obj == null) return null;
        Validate.notBlank(methodName, "methodName can't be blank");

        // 循环向上
        for (Class<?> searchType = obj.getClass(); searchType != Object.class; searchType = searchType.getSuperclass()){
            Method[] methods = searchType.getDeclaredMethods();
            for (Method method : methods) {
                // 如果方法名与参数对等
                if(method.getName().equals(methodName) && method.getParameterTypes().length == argsNum){
                    makeAccessible(method);
                    return method;
                }
            }
        }

        return null;
    }

    /**
     * 暴力破解权限修饰符
     * @param method
     */
    public static void makeAccessible(Method method){
        if ((!Modifier.isPublic(method.getModifiers()) || !Modifier.isPublic(method.getDeclaringClass().getModifiers()))
                && !method.isAccessible()){
            method.setAccessible(true);
        }
    }

}
