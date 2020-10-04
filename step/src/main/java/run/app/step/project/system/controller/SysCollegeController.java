package run.app.step.project.system.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import run.app.step.common.support.AjaxResult;
import run.app.step.framework.web.controller.BaseController;
import run.app.step.project.system.entity.SysCollege;
import run.app.step.project.system.entity.param.system.college.CollegeQuery;
import run.app.step.project.system.entity.param.system.college.SysCollegeParam;
import run.app.step.project.system.service.SysCollegeService;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author lingSong
 * @since 2020-09-07
 */
@RestController
@RequestMapping("/system/college")
@Api(tags = "系统模块-学院管理")
@CrossOrigin(origins = {"*"}, allowCredentials = "true")
public class SysCollegeController extends BaseController {

    @Autowired
    private SysCollegeService collegeService;

    @PostMapping("/list")
    @ApiOperation(value = "查询学院列表")
    public AjaxResult list(@RequestBody(required = false) CollegeQuery collegeQuery){
        List<SysCollege> list = collegeService.selectCollegeList(collegeQuery);

        return AjaxResult.ok().data("collegeList", list);
    }

    @GetMapping("/treeselect")
    @ApiOperation(value = "获取部门下拉树列表")
    public AjaxResult treeselect(){
        List<SysCollege> colleges = collegeService.selectCollegeList(new CollegeQuery());

        return AjaxResult.ok().data("collegeList", collegeService.buildCollegeTreeSelect(colleges));
    }

    @PostMapping
    @ApiOperation(value = "新增学院信息")
    public AjaxResult createBy(@Validated @RequestBody SysCollegeParam collegeParam){
        collegeService.insertCollege(collegeParam);

        return AjaxResult.ok();
    }


    @GetMapping(value = "/{id}")
    @ApiOperation(value = "根据学院id查询学院信息")
    public AjaxResult obtionInfo(@PathVariable(value = "id")String id){
        SysCollege college = collegeService.selectCollegeById(id);

        return AjaxResult.ok().data("college", college);
    }


    @PutMapping
    @ApiOperation(value = "修改目前选中的学院信息")
    public AjaxResult edit(@Validated @RequestBody SysCollegeParam collegeParam){
        // convert to
        SysCollege college = collegeParam.convertTo();

        collegeService.updateCollege(college);
        return AjaxResult.ok();
    }

    @DeleteMapping(value = "/{id}")
    @ApiOperation(value = "删除目前选中的学院信息")
    public AjaxResult delete(@PathVariable(value = "id")String id){
        collegeService.deleteById(id);

        return AjaxResult.ok();
    }

}

