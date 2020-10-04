package run.app.step.project.common;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import run.app.step.common.support.AjaxResult;
import run.app.step.common.utils.aliyun.AliyunUtil;
import run.app.step.project.system.entity.vo.course.VideoVO;

/**
 * @author lingSong
 * @date 2020/9/19 16:19
 */
@RestController
@RequestMapping(value = "/upload")
@Api(tags = "通用模块-文件上传")
@CrossOrigin(origins = {"*"}, allowCredentials = "true")
public class UploadController {

    @PostMapping
    @ApiOperation(value = "上传文件到aliyun")
    public AjaxResult uploadFile(MultipartFile file){

        return AjaxResult.ok().data("videoVO", new VideoVO(AliyunUtil.uploadMultipartFile(file),
                                                        file.getOriginalFilename().substring(0, file.getOriginalFilename().lastIndexOf(".")),
                                                        AliyunUtil.obtainDuration(file),
                                                        file.getSize()));
    }

}
