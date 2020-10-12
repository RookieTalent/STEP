package run.app.step.project.system.controller;


import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import run.app.step.common.support.AjaxResult;
import run.app.step.common.utils.poi.ExcelUtil;
import run.app.step.framework.web.controller.BaseController;
import run.app.step.project.system.entity.SysLog;
import run.app.step.project.system.entity.param.system.log.OperLogQuery;
import run.app.step.project.system.entity.vo.PageVO;
import run.app.step.project.system.service.SysLogService;

import java.util.List;

/**
 * <p>
 * 系统日志 前端控制器
 * </p>
 *
 * @author lingSong
 * @since 2020-10-09
 */
@RestController
@RequestMapping("/system/log")
public class SysLogController extends BaseController {

    @Autowired
    private SysLogService logService;

    @PostMapping("/list")
    @ApiOperation(value = "日志列表")
    public PageVO list(@RequestBody OperLogQuery query){
        startPage(query.getPageNum(), query.getPageSize());
        List<SysLog> list = logService.selectOperLogList(query);

        return getDataTable(list);
    }

    @DeleteMapping("/{operIds}")
    @ApiOperation(value = "可批量删除")
    public AjaxResult remove(@PathVariable Long[] operIds){
        logService.deleteOperLogByIds(operIds);
        return AjaxResult.ok();
    }

    @DeleteMapping("/clean")
    @ApiOperation(value = "清空日志表")
    public AjaxResult clean(){
        logService.cleanOperLog();
        return AjaxResult.ok();
    }

    @GetMapping("/export")
    @ApiOperation(value = "导出日志Excel表")
    public AjaxResult export(){
        List<SysLog> list = logService.selectOperLogList(new OperLogQuery());
        ExcelUtil<SysLog> util = new ExcelUtil<>(SysLog.class);
        return util.exportExcel(list, "操作日志");
    }

}

