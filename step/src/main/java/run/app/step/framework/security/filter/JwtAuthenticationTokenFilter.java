package run.app.step.framework.security.filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import run.app.step.common.constants.Constants;
import run.app.step.common.constants.JwtConstants;
import run.app.step.common.utils.ServiceUtil;
import run.app.step.common.utils.StringUtils;
import run.app.step.framework.cache.RedisService;
import run.app.step.framework.security.LoginUser;
import run.app.step.framework.security.utils.JwtTokenUtil;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * @author lingSong
 * @date 2020/9/28 15:48
 */
@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    @Autowired
    private RedisService redisService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        String userKey = JwtTokenUtil.obtainLoginUser(request.getHeader(Constants.ACCESS_TOKEN));
        LoginUser loginUser = (LoginUser) redisService.get(userKey);

        if(StringUtils.isNotNull(loginUser) && StringUtils.isNull(ServiceUtil.getAuthentication())){
            if(JwtTokenUtil.verifyToken(loginUser)){
                String newUserKey = JwtTokenUtil.getTokenKey(loginUser.getToken());
                redisService.set(newUserKey, loginUser, JwtConstants.ACCESS_TOKEN_EXPIRETIME.toMillis(), TimeUnit.MILLISECONDS);
            }

            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginUser, null, loginUser.getAuthorities());
            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }

        chain.doFilter(request, response);
    }

}
