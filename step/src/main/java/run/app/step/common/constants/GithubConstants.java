package run.app.step.common.constants;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author lingSong
 * @date 2020/8/14 17:32
 */
@Component
public class GithubConstants implements InitializingBean {

    @Value("${github.client.id}")
    private String clientId;

    @Value("${github.client.secret}")
    private String clientSecret;

    @Value("${github.redirect.uri}")
    private String redirectUri;


    public static String CLIENT_ID;
    public static String CLIENT_SECRET;
    public static String REDIRECT_URL;

    @Override
    public void afterPropertiesSet() throws Exception {
        CLIENT_ID = clientId;
        CLIENT_SECRET = clientSecret;
        REDIRECT_URL = redirectUri;
    }
}
