package run.app.step.project.common;

import cn.hutool.core.util.IdUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import run.app.step.common.constants.Constants;
import run.app.step.common.support.AjaxResult;
import run.app.step.common.utils.Base64;
import run.app.step.common.utils.VerifyCodeUtils;
import run.app.step.framework.cache.RedisService;

import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * @author lingSong
 * @date 2020/8/14 17:30
 */
@RestController
@Api(tags = "通用模块-用户登录验证码模块")
@CrossOrigin(origins = {"*"}, allowCredentials = "true")
public class CaptchaController {

    @Autowired
    private RedisService redisService;

    /**
     * 生成随机验证码
     * TODO 捕获IO异常
     * @return
     * @throws IOException
     */
    @GetMapping("/captchaImage")
    @ApiOperation(value = "获取验证码图片和唯一标识")
    public AjaxResult getCode() throws IOException {
        //生成随机字符串
        String verifyCode = VerifyCodeUtils.generateVerifyCode(4);

        //唯一标识
        String uuid = IdUtil.simpleUUID();
        String verifyKey = Constants.CAPTCHA_CODE_KEY + uuid;

        //存入redis
        redisService.set(verifyKey, verifyCode, Constants.CAPTCHA_EXPIRATION, TimeUnit.MINUTES);

        //生成图片
        int w = 111, h=36;
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        VerifyCodeUtils.outputImage(w, h, stream, verifyCode);
        try{
            return AjaxResult.ok().data("img", Base64.encode(stream.toByteArray())).data("uuid", uuid);
        }catch (Exception e){
            return AjaxResult.ok();
        }finally {
            stream.close();
        }
    }


}
