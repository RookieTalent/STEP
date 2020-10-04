package run.app.step.common.constants;

import lombok.Data;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Duration;

/**
 * @author lingSong
 * @date 2020/8/15 17:46
 */
@Data
@Component
public class JwtConstants implements InitializingBean {

    @Value("${jwt.secretKey}")
    private String secretKey;

    @Value("${jwt.accessTokenExpireTime}")
    private Duration accessTokenExpireTime;

    @Value("${jwt.refreshTokenExpireTime}")
    private Duration refreshTokenExpireTime;

    @Value("${jwt.issuer}")
    private String issuer;


    public static String SECRET_KEY;
    public static Duration ACCESS_TOKEN_EXPIRETIME;
    public static Duration REFRESH_TOKEN_EXPIRETIME;
    public static String ISSUER;


    @Override
    public void afterPropertiesSet() throws Exception {
        SECRET_KEY = secretKey;
        ACCESS_TOKEN_EXPIRETIME = accessTokenExpireTime;
        REFRESH_TOKEN_EXPIRETIME = refreshTokenExpireTime;
        ISSUER =issuer;
    }
}
