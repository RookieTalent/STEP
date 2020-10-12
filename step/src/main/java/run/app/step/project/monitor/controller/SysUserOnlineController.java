package run.app.step.project.monitor.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import run.app.step.common.constants.Constants;
import run.app.step.common.utils.StringUtils;
import run.app.step.framework.cache.RedisService;
import run.app.step.framework.security.LoginUser;
import run.app.step.framework.security.utils.JwtTokenUtil;
import run.app.step.framework.web.controller.BaseController;
import run.app.step.project.monitor.entity.SysUserOnline;
import run.app.step.project.monitor.service.SysUserOnlineService;
import run.app.step.project.system.entity.vo.PageVO;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * @author lingSong
 * @date 2020/9/27 17:09
 */
@RestController
@RequestMapping("/monitor/online")
@Api(tags = "系统监控-在线人数")
public class SysUserOnlineController extends BaseController {

    @Autowired
    private RedisService redisService;

    @Autowired
    private SysUserOnlineService userOnlineService;

    @GetMapping("/list")
    @ApiOperation(value = "获取在线人数信息")
    public PageVO list(String ipaddr, String nickName){
        // 查询redis缓存的token
        Set<String> keys = redisService.keys(Constants.LOGIN_TOKEN_KEY + "*");
        // 新建在线用户列表
        List<SysUserOnline> userOnlineList = new ArrayList<>();

        // 循环遍历
        for (String key : keys) {
            LoginUser user = (LoginUser) redisService.get(key);
            // 如果传入了ipaddr
            if(StringUtils.isNotEmpty(ipaddr) && StringUtils.isNotEmpty(nickName)){
                userOnlineList.add(userOnlineService.selectOnlineByInfo(ipaddr, nickName, user));
            }else if(StringUtils.isNotEmpty(ipaddr)){
                userOnlineList.add(userOnlineService.selectOnlineByIpaddr(ipaddr, user));
            }else if(StringUtils.isNotEmpty(nickName) && StringUtils.isNotNull(user.getUser())){
                userOnlineList.add(userOnlineService.selectOnlineByNickName(nickName, user));
            }else {
                userOnlineList.add(userOnlineService.loginUserToUserOnline(user));
            }
        }
        Collections.reverse(userOnlineList);
        // 优化
        userOnlineList.removeAll(Collections.singleton(null));

        return getDataTable(userOnlineList, true);
    }
}
