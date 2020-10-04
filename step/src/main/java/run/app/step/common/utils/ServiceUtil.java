package run.app.step.common.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import run.app.step.common.constants.Constants;
import run.app.step.common.exception.BadRequestException;
import run.app.step.common.exception.DBException;
import run.app.step.framework.security.LoginUser;
import run.app.step.framework.security.utils.JwtTokenUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author lingSong
 * @date 2020/9/10 16:28
 */
@Slf4j
public class ServiceUtil {

    /**
     * 校验成果并抛出异常
     *
     * @param result
     * @param message
     */
    public static void throwsException(int result, String message){
        if(result == 0){
            throw new DBException(message);
        }
    }

    /**
     * 返回当前请求用户昵称
     *
     * @param request
     * @return
     */
    public static String obtainOperator(HttpServletRequest request){
        return JwtTokenUtil.obtainNickName(request.getHeader(Constants.ACCESS_TOKEN));
    }


    /**
     * 获取request
     *
     * @return
     */
    public static HttpServletRequest  getRequest(){
        return getRequestAttributes().getRequest();
    }

    /**
     * 获取ServletRequestAttributes
     *
     * @return
     */
    public static ServletRequestAttributes getRequestAttributes(){
        RequestAttributes attributes = RequestContextHolder.getRequestAttributes();
        return (ServletRequestAttributes) attributes;
    }

    /**
     * 将字符串渲染到客户端
     *
     * @param response 渲染对象
     * @param string 待渲染的字符串
     * @return null
     */
    public static String renderString(HttpServletResponse response, String string)
    {
        try
        {
            response.setStatus(200);
            response.setContentType("application/json");
            response.setCharacterEncoding("utf-8");
            response.getWriter().print(string);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取用户账号
     *
     * @return
     */
    public static String getNickName(){
        try {
            return getLoginUser().getUser().getNickname();
        }catch (Exception e){
            throw new BadRequestException("获取用户账户异常");
        }
    }


    /**
     * 获取用户
     */
    public static LoginUser getLoginUser(){
        try {
            return (LoginUser) getAuthentication().getPrincipal();
        }catch (Exception e){
            throw new BadRequestException("获取用户信息异常");
        }
    }

    /**
     * 获取Authentication
     */
    public static Authentication getAuthentication(){
        return SecurityContextHolder.getContext().getAuthentication();
    }

}
