package run.app.step.framework.web.service;

import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import run.app.step.common.constants.Constants;
import run.app.step.framework.cache.RedisService;
import run.app.step.framework.security.utils.JwtTokenUtil;
import run.app.step.project.system.service.SysUserService;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author lingSong
 * @date 2020/8/28 15:18
 */
@Slf4j
@Service
public class SmsService {

    @Autowired
    private RedisService redisService;

    @Autowired
    private SysUserService userService;

    /**
     * 向手机发送验证码
     * @param access_token
     * @param code
     * @return
     */
    public boolean send(String access_token, String code, String mobile) {
        Assert.notNull(access_token, "access token must not be null");

        if(StringUtils.isEmpty(mobile)){
            return false;
        }

        Map<String, Object> param = new HashMap<String, Object>();
        param.put("code", code);

        DefaultProfile profile =
                DefaultProfile.getProfile("default", "LTAI4GE8bGXx52uER3GnMeai", "TTUOd2UV9rC44NOYwkCqsynBV7eMTs");
        IAcsClient client = new DefaultAcsClient(profile);

        //设置固定相关参数
        CommonRequest request = new CommonRequest();
        //request.setProtocol(ProtocolType.HTTPS);
        request.setMethod(MethodType.POST);
        request.setDomain("dysmsapi.aliyuncs.com");
        request.setVersion("2017-05-25");
        request.setAction("SendSms");

        //设置发送相关参数
        request.putQueryParameter("PhoneNumbers", mobile);
        request.putQueryParameter("SignName", "个人英语在线教育网站");        //签名名称
        request.putQueryParameter("TemplateCode", "SMS_198931740");          //模板Code
        request.putQueryParameter("TemplateParam", JSONObject.toJSONString(param));

        //存入redis
        redisService.set(Constants.SMS_CAPTCHA_CODE_KEY+mobile, code, Constants.SMS_CAPTCHA_EXPIRATION, TimeUnit.MINUTES);

        try {
            CommonResponse response = client.getCommonResponse(request);
            return response.getHttpResponse().isSuccess();
        } catch (ClientException e) {
            log.error("短信发送失败");
            return false;
        }

    }
}
