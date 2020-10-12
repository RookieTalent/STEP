package run.app.step.framework.aspectj;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import run.app.step.common.constants.Constants;
import run.app.step.common.utils.IpUtils;
import run.app.step.common.utils.ServiceUtil;
import run.app.step.framework.aspectj.lang.annotation.AutoLog;
import run.app.step.framework.cache.RedisService;
import run.app.step.framework.security.LoginUser;
import run.app.step.framework.security.utils.JwtTokenUtil;
import run.app.step.project.system.entity.SysLog;
import run.app.step.project.system.mapper.SysLogMapper;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

/**
 * @author lingSong
 * @date 2020/8/30 11:49
 */
@Slf4j
@Aspect
@Component
public class AutoLogAspect {

    @Autowired
    private SysLogMapper sysLogMapper;

    @Autowired
    private RedisService redisService;

    @Pointcut("@annotation(run.app.step.framework.aspectj.lang.annotation.AutoLog)")
    public void loginPointCut(){
    }

    /**
     * 环绕增强
     *
     * @param point
     * @return
     */
    @Around("loginPointCut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        long beginTime = System.currentTimeMillis();
        // 执行方法
        Object result = point.proceed();
        // 执行时长
        long time = System.currentTimeMillis() - beginTime;

        // 保存日志
        try {
            saveLog(point, time);
        }catch (Exception e){
            log.error("auto log error:[{}]", e);
        }

        return result;
    }

    /**
     * 保存日志
     *
     * @param point
     * @param time
     */
    private void saveLog(ProceedingJoinPoint point, long time){
        MethodSignature signature = (MethodSignature)point.getSignature();
        Method method = signature.getMethod();

        SysLog sysLog = new SysLog();
        AutoLog autoLog = method.getAnnotation(AutoLog.class);
        if(autoLog != null){
            // 获取描述
            sysLog.setOperation(autoLog.title()+"-"+autoLog.action());
        }

        // 请求的类名-方法名
        String className = point.getTarget().getClass().getName();
        String methodName = signature.getName();
        sysLog.setMethod(className+"."+methodName+"()");
        sysLog.setMethodMode(autoLog.businessType().toString());

        // 打印耗时
        log.info("请求{}.{}耗时{}毫秒", className, methodName, time);

        try {
            Object[] args = point.getArgs();
            String params = null;
            if(args.length != 0){
                params = JSON.toJSONString(args);
            }
            sysLog.setParams(params);
        }catch (Exception  e){
            log.error("error={}", e);
        }

        HttpServletRequest request = ServiceUtil.getRequest();
        sysLog.setIp(IpUtils.getIpAddr(request));
        log.info("IP{}, 接口地址{}, 请求方式{}， 入参:{}",
                sysLog.getIp(), request.getRequestURL(), request.getMethod(), sysLog.getParams());

        String token = request.getHeader(Constants.ACCESS_TOKEN);
        String uuid = JwtTokenUtil.obtainLoginUser(token);
        LoginUser loginUser = (LoginUser) redisService.get(uuid);

        sysLog.setNickname(loginUser.getUsername());
        sysLog.setUserId(loginUser.getUser().getId());
        sysLog.setTime((int)time);

        sysLogMapper.insert(sysLog);
    }
}
