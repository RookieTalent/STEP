package run.app.step.framework.security.utils;

import cn.hutool.core.lang.UUID;
import com.sun.org.apache.xpath.internal.operations.Bool;
import eu.bitwalker.useragentutils.UserAgent;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import run.app.step.common.constants.Constants;
import run.app.step.common.constants.JwtConstants;
import run.app.step.common.utils.AddressUtils;
import run.app.step.common.utils.IpUtils;
import run.app.step.common.utils.ServiceUtil;
import run.app.step.framework.cache.RedisService;
import run.app.step.framework.security.LoginUser;

import javax.xml.bind.DatatypeConverter;
import java.util.Date;
import java.util.Map;

/**
 * @author lingSong
 * @date 2020/8/15 17:55
 */
@Slf4j
@Component
public class JwtTokenUtil {

    /**
     * 20分钟
     */
    private static final Long MILLIS_MINUTE_TEN = 20 * 60 * 1000L;

    /**
     * 签发accessToken
     * PT2H
     * @param subject
     * @param claims
     * @return
     */
    public static String getAccesToken(String subject, Map<String, Object> claims){
        return generateToken(JwtConstants.ISSUER, subject, claims, JwtConstants.ACCESS_TOKEN_EXPIRETIME.toMillis(), JwtConstants.SECRET_KEY);
    }

    /**
     * 签发refreshToken
     * PT8H
     * @param subject
     * @param claims
     * @return
     */
    public static String getRefreshToken(String subject, Map<String, Object> claims){
        return generateToken(JwtConstants.ISSUER, subject, claims, JwtConstants.REFRESH_TOKEN_EXPIRETIME.toMillis(), JwtConstants.SECRET_KEY);
    }


    /**
     * 构建token 分accessToken和refershToken
     * @param issuer 签发人
     * @param subject 主体 主要是用户id
     * @param claims 存储JWT里面的信息， 一般都是存放用户的角色和权限
     * @param ttlMillis 期限
     * @param secret 密匙
     * @return
     */
    public static String generateToken(String issuer, String subject, Map<String, Object> claims,
                                       long ttlMillis, String secret){
        //设置加密算法
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

        //获取当前时间
        long timeMillis = System.currentTimeMillis();
        Date now = new Date(timeMillis);

        //加密签发
        byte[] signKey = DatatypeConverter.parseBase64Binary(secret);
        JwtBuilder builder = Jwts.builder();

        if(null != claims){
            builder.setClaims(claims);
        }
        if(StringUtils.isNotEmpty(subject)){
            builder.setSubject(subject);
        }
        if(StringUtils.isNotEmpty(issuer)){
            builder.setIssuer(issuer);
        }
        //于什么时候构建
        builder.setIssuedAt(now);
        if(ttlMillis > 0){
            //token过期的时间是现在加你设定的时限
            long expMillis = timeMillis + ttlMillis;
            Date exp = new Date(expMillis);
            builder.setExpiration(exp);
        }
        //最后设置加密算法和加密过后的密匙
        builder.signWith(signatureAlgorithm, signKey);

        return builder.compact();
    }

    /*==================解析令牌========================*/

    /**
     * 创建载荷内容
     *
     * @param claims
     * @param loginUser
     * @return
     */
    public static void setClaims(Map<String, Object> claims, LoginUser loginUser){
        String token = UUID.fastUUID().toString();
        loginUser.setToken(token);
        loginUser.setLoginTime(System.currentTimeMillis());
        //TODO 时间不对
        loginUser.setExpireTime(loginUser.getLoginTime() + JwtConstants.ACCESS_TOKEN_EXPIRETIME.toMillis());

        claims.put(Constants.LOGIN_USER_KEY, token);

        setUserAgent(loginUser);
    }

    /**
     * 设置用户代理信息
     *
     * @param loginUser
     */
    public static void setUserAgent(LoginUser loginUser){
        UserAgent userAgent = UserAgent.parseUserAgentString(ServiceUtil.getRequest().getHeader("User-Agent"));
        String ip = IpUtils.getIpAddr(ServiceUtil.getRequest());
        loginUser.setIpaddr(ip);
        loginUser.setLoginLocation(AddressUtils.getRealAddressByIP(ip));
        loginUser.setBrowser(userAgent.getBrowser().getName());
        loginUser.setOs(userAgent.getOperatingSystem().getName());
    }

    /**
     * 从令牌中获取载荷
     * @param token
     * @return
     */
    public static Claims getClaimsFromToken(String token){
        Claims claims;
        try{
            claims = Jwts.parser().setSigningKey(DatatypeConverter.parseBase64Binary(JwtConstants.SECRET_KEY))
                    .parseClaimsJws(token)
                    .getBody();
        }catch (Exception e){
            claims = null;
        }
        return claims;
    }

    /**
     * TODO
     * 获取用户id
     * @param token
     * @return
     */
    public static String obtainUserId(String token){
        String userId = null;
        try{
            Claims claims = getClaimsFromToken(token);
            userId = claims.getSubject();
        }catch (Exception e){
            log.error("Cannot obtain user id, error:{}", e.getMessage());
        }
        return userId;
    }

    /**
     * TODO
     * 获取用户名称
     * @param token
     * @return
     */
    public static String obtainNickName(String token){
        String nickname = null;
        try{
            Claims claims = getClaimsFromToken(token);
            nickname = (String)claims.get(Constants.JWT_Nick_NAME);
        }catch (Exception e){
            log.error("Cannot obtain username, error:{}", e.getMessage());
        }
        return nickname;
    }


    public static String obtainLoginUser(String token){
        String uuid = null;
        try{
            Claims claims = getClaimsFromToken(token);
            // 解析对应的权限以及用户信息
            uuid = (String) claims.get(Constants.LOGIN_USER_KEY);
            String userKey = getTokenKey(uuid);
            return userKey;
        }catch (Exception e){
            log.error("Cannot obtain login user, error:{}", e.getMessage());
        }

        return null;
    }

    /**
     * 检测是否已经过期
     * @param token
     * @return
     */
    public static Boolean isTokenExp(String token){
        try {
            Claims claims = getClaimsFromToken(token);
            Date expiration = claims.getExpiration();
            return expiration.before(new Date());
        }catch (Exception e){
            log.error("Cannot verify whether the token has expired, error:{}", e.getMessage());
            return true;
        }
    }

    /**
     * 校验令牌
     * @param token
     * @return
     */
    public static Boolean validateToken(String token){
        Claims claims = getClaimsFromToken(token);
        return (null != claims && !isTokenExp(token));
    }

    /**
     * 获取剩余时间
     * @param token
     * @return
     */
    public static long getRemainingTime(String token){
        long result = 0;
        try{
            long nowMillis = System.currentTimeMillis();
            result = getClaimsFromToken(token).getExpiration().getTime()-nowMillis;
        }catch (Exception e){
            log.error("unable to get the remaining time, error:{}", e.getMessage());
        }
        return result;
    }

    /**
     * 校验令牌有效时间  不足20分钟 自动刷新缓存
     * @param loginUser
     */
    public static boolean verifyToken(LoginUser loginUser){
        Long expireTime = loginUser.getExpireTime();
        long currentTime = System.currentTimeMillis();
        if(expireTime - currentTime <= MILLIS_MINUTE_TEN){
            refreshToken(loginUser);
            return true;
        }
        return false;
    }

    /**
     * 刷新token
     * TODO 待定刷新时间是否需要延迟
     * @param loginUser
     */
    public static void refreshToken(LoginUser loginUser){
        loginUser.setLoginTime(System.currentTimeMillis());
        loginUser.setExpireTime(loginUser.getLoginTime() + JwtConstants.ACCESS_TOKEN_EXPIRETIME.toMillis());
    }

    public static String getTokenKey(String uuid){
        return Constants.LOGIN_TOKEN_KEY+uuid;
    }

}
