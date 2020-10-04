package run.app.step.project.system.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import run.app.step.common.constants.Constants;
import run.app.step.common.support.AjaxResult;
import run.app.step.common.utils.ServiceUtil;
import run.app.step.common.utils.poi.ExcelUtil;
import run.app.step.framework.security.utils.JwtTokenUtil;
import run.app.step.framework.web.controller.BaseController;
import run.app.step.project.system.entity.SysDictData;
import run.app.step.project.system.entity.param.system.dict.DictDataQuery;
import run.app.step.project.system.entity.vo.PageVO;
import run.app.step.project.system.service.SysDictDataService;
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
@RequestMapping("/system/dict/data")
@Api(tags = "系统模块-字典数据管理")
@CrossOrigin(origins = {"*"}, allowCredentials = "true")
public class SysDictDataController extends BaseController {

    @Autowired
    private SysDictDataService dictDataService;

    @Autowired
    private SysDictTypeService dictTypeService;

    @PostMapping
    @ApiOperation(value = "插入字典数据")
    public AjaxResult insert(@RequestBody SysDictData dictData, HttpServletRequest request){
        dictData.setCreateBy(ServiceUtil.obtainOperator(request));

        dictDataService.insertDictData(dictData);
        return AjaxResult.ok();
    }

    @GetMapping("/list")
    @ApiOperation(value = "字典数据列表")
    public PageVO list(DictDataQuery dataQuery){
        startPage(dataQuery.getPageNum(), dataQuery.getPageSize());
        List<SysDictData> list = dictDataService.selectDictDataList(dataQuery);

        return getDataTable(list);
    }

    @GetMapping("/{dictCode}")
    @ApiOperation(value = "根据编码查询字典数据")
    public AjaxResult getInfo(@PathVariable Long dictCode){
        return AjaxResult.ok().data("dictData", dictDataService.selectDictDataById(dictCode));
    }

    @GetMapping("/type/{dictType}")
    @ApiOperation(value = "根据字典类型查询字典数据信息")
    public AjaxResult dictType(@PathVariable String dictType){
        List<SysDictData> list = dictTypeService.selectDictDataByType(dictType);
        return AjaxResult.ok().data("list", list);
    }

    @PutMapping
    @ApiOperation(value = "修改字典数据")
    public AjaxResult update(@RequestBody SysDictData dictData, HttpServletRequest request){
        dictData.setUpdateBy(ServiceUtil.obtainOperator(request));

        dictDataService.updateDictData(dictData);
        return AjaxResult.ok();
    }

    @DeleteMapping("/{dictCodes}")
    @ApiOperation(value = "批量/单次删除字典数据")
    public AjaxResult delete(@PathVariable Long[] dictCodes){
        dictDataService.deleteDictDataByIds(dictCodes);

        return AjaxResult.ok();
    }

    @GetMapping("/export")
    @ApiOperation(value = "导出数据库到xlsx表")
    public AjaxResult export(){
        List<SysDictData> list = dictDataService.selectDictDataList(new DictDataQuery());
        ExcelUtil<SysDictData> util = new ExcelUtil<>(SysDictData.class);

        return util.exportExcel(list, "字典数据");
    }
}
