package run.app.step.common.utils.encode;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.UUID;

/**
 * 个人感觉加盐值更安全一些
 *
 * @author lingSong
 * @date 2020/8/15 21:01
 */
public class PasswordUtils {

    /**
     * 匹配密码
     * @param salt 盐值
     * @param rawPass 明文
     * @param encPass 密文
     * @return
     */
    public static boolean matches(String salt, String rawPass, String encPass) {
        return new PasswordEncoder(salt).matches(encPass, rawPass);
    }


    /**
     * shiro式
     * 加密明文
     * @param rawPass
     * @param salt
     * @return
     */
    public static String encode(String rawPass, String salt){
        return new PasswordEncoder(salt).encode(rawPass);
    }

    /**
     * shiro式
     * 获取盐值 或者
     * IdUtils.simpleUUID() 也可以
     * @return
     */
    public static String getSalt(){
        return UUID.randomUUID().toString().replaceAll("-", "").substring(0,20);
    }

    /**
     * spring security式
     * 加密明文
     * @param password
     * @return
     */
    public static String encryptPassword(String password){
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.encode(password);
    }

    /**
     * spring security式
     * 判断密码是否相同
     * @param rawPassword 真实密码
     * @param encodePassword 加密后字符
     * @return
     */
    public static boolean matchesPassword(String rawPassword, String encodePassword){
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.matches(rawPassword, encodePassword);
    }

}
