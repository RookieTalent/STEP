package run.app.step.project.system.controller;


import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;
import run.app.step.project.system.service.SysChapterService;
import run.app.step.project.system.service.SysCourseService;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author lingSong
 * @since 2020-09-16
 */
@RestController
@RequestMapping("/system/course")
@Api(tags = "系统模块-课程管理")
@CrossOrigin(origins = {"*"}, allowCredentials = "true")
public class SysCourseController {

    @Autowired
    private SysCourseService courseService;

    @Autowired
    private SysChapterService chapterService;


}

