package run.app.step.project.system.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import run.app.step.common.constants.Constants;
import run.app.step.common.enums.code.BusinessType;
import run.app.step.common.support.AjaxResult;
import run.app.step.common.utils.ServiceUtil;
import run.app.step.common.utils.poi.ExcelUtil;
import run.app.step.framework.aspectj.lang.annotation.AutoLog;
import run.app.step.framework.security.utils.JwtTokenUtil;
import run.app.step.framework.web.controller.BaseController;
import run.app.step.project.system.entity.SysDictType;
import run.app.step.project.system.entity.param.system.dict.DictTypeQuery;
import run.app.step.project.system.entity.vo.PageVO;
import run.app.step.project.system.service.SysDictTypeService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author lingSong
 * @since 2020-09-22
 */
@RestController
@RequestMapping("/system/dict/type")
@Api(tags = "系统模块-数据字典类型管理")
@CrossOrigin(origins = {"*"}, allowCredentials = "true")
public class SysDictTypeController extends BaseController {

    @Autowired
    private SysDictTypeService dictTypeService;

    @GetMapping("/list")
    @ApiOperation(value = "数据类型列表")
    @AutoLog(title = "系统模块-数据字典类型管理", action = "数据类型列表", businessType = BusinessType.LIST)
    public PageVO list(DictTypeQuery query){
        startPage(query.getPageNum(), query.getPageSize());
        List<SysDictType> list =  dictTypeService.selectDictTypeList(query);

        return getDataTable(list);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "根据id查询数字字典类型")
    @PreAuthorize("@ss.hasPermi('system:dict:query')")
    @AutoLog(title = "系统模块-数据字典类型管理", action = "单体查询", businessType = BusinessType.OTHER)
    public AjaxResult getInfo(@PathVariable(value = "id") Long id){
        return AjaxResult.ok().data("dictType", dictTypeService.selectDictTypeById(id));
    }


    @PostMapping
    @ApiOperation(value = "添加字典类型")
    @PreAuthorize("@ss.hasPermi('system:dict:add')")
    @AutoLog(title = "系统模块-数据字典类型管理", action = "新增数据类型", businessType = BusinessType.INSERT)
    public AjaxResult insert(@RequestBody SysDictType dictType, HttpServletRequest request){
        dictType.setCreateBy(ServiceUtil.getNickName());
        dictTypeService.insertDictType(dictType);

        return AjaxResult.ok();
    }

    @PutMapping
    @ApiOperation(value = "修改字典类型")
    @PreAuthorize("@ss.hasPermi('system:dict:edit')")
    @AutoLog(title = "系统模块-数据字典类型管理", action = "修改字典类型", businessType = BusinessType.UPDATE)
    public AjaxResult edit(@RequestBody SysDictType dictType, HttpServletRequest request){
        dictType.setUpdateBy(JwtTokenUtil.obtainNickName(request.getHeader(Constants.ACCESS_TOKEN)));

        dictTypeService.updateDictType(dictType);
        return AjaxResult.ok();
    }

    @DeleteMapping("/{dictIds}")
    @ApiOperation(value = "单/批量删除字典数据类型")
    @PreAuthorize("@ss.hasPermi('system:dict:remove')")
    @AutoLog(title = "系统模块-数据字典类型管理", action = "删除字典类型", businessType = BusinessType.DELETE)
    public AjaxResult remove(@PathVariable Long[] dictIds){
        dictTypeService.deleteDictTypeByIds(dictIds);

        return AjaxResult.ok();
    }

    @GetMapping("/export")
    @ApiOperation(value = "导出数据库数据至xlsx表")
    @PreAuthorize("@ss.hasPermi('system:dict:export')")
    @AutoLog(title = "系统模块-数据字典类型管理", action = "导出数据类型", businessType = BusinessType.EXPORT)
    public AjaxResult export(){
        List<SysDictType> list = dictTypeService.selectDictTypeList(new DictTypeQuery());
        ExcelUtil<SysDictType> util = new ExcelUtil<>(SysDictType.class);

        return util.exportExcel(list, "字典类型数据");
    }

    @DeleteMapping("/clearCache")
    @ApiOperation(value = "清空字典缓存")
    @PreAuthorize("@ss.hasPermi('system:dict:remove')")
    @AutoLog(title = "系统模块-数据字典类型管理", action = "清空缓存", businessType = BusinessType.OTHER)
    public AjaxResult clearCache(){
        dictTypeService.clearCache();

        return AjaxResult.ok();
    }
}

