package run.app.step.project.monitor.controller;

import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import run.app.step.project.monitor.service.SysUserOnlineService;

/**
 * @author lingSong
 * @date 2020/9/27 17:09
 */
@RestController
@RequestMapping("/monitor/online")
@Api(tags = "系统监控-在线人数")
public class SysUserOnlineController {

    @Autowired
    private SysUserOnlineService userOnlineService;

}
