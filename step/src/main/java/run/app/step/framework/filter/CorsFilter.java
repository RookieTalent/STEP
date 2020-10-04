package run.app.step.framework.filter;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.cors.CorsUtils;
import org.springframework.web.filter.GenericFilterBean;
import run.app.step.common.constants.Constants;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author lingSong
 * @date 2020/10/3 21:41
 */
public class CorsFilter extends GenericFilterBean {

    private final static String ALLOW_HEADERS = StringUtils.joinWith(",", HttpHeaders.CONTENT_TYPE, Constants.ACCESS_TOKEN);


    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;

        String originHeaderValue = httpServletRequest.getHeader(HttpHeaders.ORIGIN);
        if (StringUtils.isNotBlank(originHeaderValue)) {
            httpServletResponse.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, originHeaderValue);
        }
        httpServletResponse.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_HEADERS, ALLOW_HEADERS);
        httpServletResponse.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_METHODS, "GET, POST, PUT, DELETE, OPTIONS");
        httpServletResponse.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_CREDENTIALS, "true");
        httpServletResponse.setHeader(HttpHeaders.ACCESS_CONTROL_MAX_AGE, "3600");

        if (!CorsUtils.isPreFlightRequest(httpServletRequest)) {
            chain.doFilter(httpServletRequest, httpServletResponse);
        }
    }

    private boolean isPreFlightRequest(HttpServletRequest request) {
        return (HttpMethod.OPTIONS.matches(request.getMethod()) &&
                request.getHeader(HttpHeaders.ACCESS_CONTROL_REQUEST_METHOD) != null);
    }
}
