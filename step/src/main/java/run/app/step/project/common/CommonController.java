package run.app.step.project.common;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import run.app.step.common.exception.NotFoundException;
import run.app.step.common.utils.StringUtils;
import run.app.step.common.utils.file.FileUtils;
import run.app.step.framework.config.StepConfig;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author lingSong
 * @date 2020/9/6 15:29
 */
@Slf4j
@RestController
@Api(tags = "通用模块-下载模块")
@CrossOrigin(origins = {"*"}, allowCredentials = "true")
public class CommonController {


    @GetMapping("common/download")
    public void fileDownload(String fileName, Boolean delete, HttpServletResponse response, HttpServletRequest request){
        try {
            if(!FileUtils.isValidFilename(fileName)){
                throw new Exception(StringUtils.format("文件名称({})非法，不允许下载。 ", fileName));
            }

            String realFileName = System.currentTimeMillis() + fileName.substring(fileName.indexOf("_") + 1);
            String filePath = StepConfig.getDownloadPath() + fileName;

            response.setCharacterEncoding("utf-8");
            response.setContentType("multipart/form-data");
            response.setHeader("Content-Disposition",
                    "attachment;fileName=" + FileUtils.setFileDownloadHeader(request, realFileName));
            FileUtils.writeBytes(filePath, response.getOutputStream());
            if(delete){
                FileUtils.deleteFile(filePath);
            }
        }catch (Exception e){
            log.error("下载文件失败", e);
            throw new NotFoundException(fileName + " 文件下载失败");
        }
    }
}
