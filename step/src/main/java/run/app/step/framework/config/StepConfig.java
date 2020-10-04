package run.app.step.framework.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import run.app.step.framework.filter.CorsFilter;

/**
 * @author lingSong
 * @date 2020/9/2 20:33
 */
@Configuration
@ConfigurationProperties(prefix = "step")
public class StepConfig {

    /** 上传路径*/
    private static String profile;

    public static String getProfile() {
        return profile;
    }

    public static void setProfile(String profile) {
        StepConfig.profile = profile;
    }

    /**
     * 获取下载路径
     */
    public static String getDownloadPath(){
        return getProfile() + "/download/";
    }

    /**
     * 创建CORS过滤器
     */
    @Bean
    public FilterRegistrationBean<CorsFilter> corsFilter() {
        FilterRegistrationBean<CorsFilter> corsFilter = new FilterRegistrationBean<>();

        corsFilter.setOrder(Ordered.HIGHEST_PRECEDENCE + 10);
        corsFilter.setFilter(new CorsFilter());
        corsFilter.addUrlPatterns("/*");

        return corsFilter;
    }
}
