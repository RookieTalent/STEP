package run.app.step.project.system.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import run.app.step.common.enums.code.BusinessType;
import run.app.step.common.support.AjaxResult;
import run.app.step.common.utils.ServiceUtil;
import run.app.step.framework.aspectj.lang.annotation.AutoLog;
import run.app.step.framework.web.controller.BaseController;
import run.app.step.project.system.entity.SysNotice;
import run.app.step.project.system.entity.param.system.notice.NoticeQuery;
import run.app.step.project.system.entity.vo.PageVO;
import run.app.step.project.system.service.SysNoticeService;

import java.util.List;

/**
 * <p>
 * 通知公告表 前端控制器
 * </p>
 *
 * @author lingSong
 * @since 2020-10-12
 */
@RestController
@RequestMapping("/system/notice")
@Api(tags = "系统模块-公告通知")
public class SysNoticeController extends BaseController {

    @Autowired
    private SysNoticeService noticeService;

    @PostMapping("/list")
    @ApiOperation(value = "分页列表")
    @AutoLog(title = "系统模块-公告通知", action = "公告列表", businessType = BusinessType.LIST)
    public PageVO list(@RequestBody NoticeQuery query){
        startPage(query.getPageNum(), query.getPageSize());
        List<SysNotice> list = noticeService.selectNoticeList(query);
        return getDataTable(list);
    }

    @PostMapping
    @ApiOperation(value = "新增公告通知")
    @PreAuthorize("@ss.hasPermi('system:notice:add')")
    @AutoLog(title = "系统模块-公告通知", action = "新增公告", businessType = BusinessType.INSERT)
    public AjaxResult insert(@RequestBody SysNotice notice){
        notice.setCreateBy(ServiceUtil.getNickName());
        noticeService.insertNotice(notice);
        return AjaxResult.ok();
    }

    @PutMapping
    @ApiOperation(value = "修改通知公告")
    @PreAuthorize("@ss.hasPermi('system:notice:edit')")
    @AutoLog(title = "系统模块-公告通知", action = "修改公告", businessType = BusinessType.UPDATE)
    public AjaxResult edit(@RequestBody SysNotice notice){
        notice.setUpdateBy(ServiceUtil.getNickName());
        noticeService.updateNotice(notice);
        return AjaxResult.ok();
    }

    @GetMapping(value = "/{id}")
    @ApiOperation(value = "根据公告编号获取详细信息")
    @PreAuthorize("@ss.hasPermi('system:notice:query')")
    @AutoLog(title = "系统模块-公告通知", action = "单体查询", businessType = BusinessType.OTHER)
    public AjaxResult getInfo(@PathVariable Long id){
        return AjaxResult.ok().data("notice", noticeService.selectNoticeById(id));
    }

    @DeleteMapping("/{noticeIds}")
    @ApiOperation(value = "删除/批量删除")
    @PreAuthorize("@ss.hasPermi('system:notice:remove')")
    @AutoLog(title = "系统模块-公告通知", action = "删除公告", businessType = BusinessType.DELETE)
    public AjaxResult remove(@PathVariable Long[] noticeIds){
        noticeService.deleteNoticeByIds(noticeIds);
        return AjaxResult.ok();
    }

    @GetMapping("/play")
    @ApiOperation(value = "播放公告")
    @AutoLog(title = "系统模块-公告通知", action = "播放公告", businessType = BusinessType.OTHER)
    public AjaxResult play(){
        return AjaxResult.ok().data("list", noticeService.playNotice());
    }

}

