package run.app.step.project.monitor.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import run.app.step.framework.web.controller.BaseController;
import run.app.step.project.system.service.SysLogininfoService;

/**
 * @author lingSong
 * @date 2020/9/27 10:27
 */
@RestController
@RequestMapping("/monitor/logininfo")
public class SysLogininfoController extends BaseController {

    @Autowired
    private SysLogininfoService logininfoService;



}
