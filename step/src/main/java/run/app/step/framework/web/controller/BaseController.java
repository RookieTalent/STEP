package run.app.step.framework.web.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.util.Assert;
import run.app.step.project.system.entity.vo.PageVO;

import java.util.List;

/**
 *
 * web通用层
 *
 * @author lingSong
 * @date 2020/8/24 15:37
 */
public class BaseController {

    /**
     * 设置请求分页数据
     * @param pageNum
     * @param pageSize
     */
    protected void startPage(Integer pageNum, Integer pageSize){
        Assert.notNull(pageNum, "page num must not be null");
        Assert.notNull(pageSize, "page size must not be null");

        PageHelper.startPage(pageNum, pageSize);
    }

    protected PageVO getDataTable(List<?> list){
        PageVO result = new PageVO();

        if(list instanceof Page){
            Page<?> page = (Page<?>)list;

            result.setHttpStatus(HttpStatus.OK);
            result.setMsg("查询成功");
            result.setRows(page.getResult());
            result.setTotal(page.getTotal());
        }

        return result;
    }


}
