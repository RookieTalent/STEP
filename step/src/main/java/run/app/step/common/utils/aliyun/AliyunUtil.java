package run.app.step.common.utils.aliyun;

import it.sauronsoftware.jave.Encoder;
import it.sauronsoftware.jave.MultimediaInfo;
import org.springframework.web.multipart.MultipartFile;
import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadStreamRequest;
import com.aliyun.vod.upload.resp.UploadStreamResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import run.app.step.common.constants.VodConstants;
import run.app.step.common.exception.BadRequestException;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

/**
 * @author lingSong
 * @date 2020/9/19 16:22
 */
public class AliyunUtil {

    /**
     * 上传文件到oss
     * aliyun有相关API
     *
     * @param file
     * @return
     */
    public static String uploadMultipartFile(MultipartFile file){
        String fileName = file.getOriginalFilename();
        String title = fileName.substring(0, fileName.lastIndexOf("."));

        InputStream inputStream = null;
        try {
            inputStream = file.getInputStream();
        } catch (IOException e) {
            return "";
        }

        //fileName: 上传文件原始名称 title:阿里云上传之后的名称
        UploadStreamRequest request = new UploadStreamRequest(VodConstants.ACCESS_KEY_ID, VodConstants.ACCESS_KEY_SECRET,
                title, fileName, inputStream);

        UploadVideoImpl uploader = new UploadVideoImpl();
        UploadStreamResponse response = uploader.uploadStream(request);

        // 请求视频点播的请求id
        String videoId = null;
        if(response.isSuccess()){
            videoId = response.getVideoId();
        }else{
            //如果设置回调URL无效，不影响视频上传，可以返回VideoId同时会返回错误码。其他情况上传失败时，VideoId为空，此时需要根据返回错误码分析具体错误原因
            videoId = response.getVideoId();
        }

        return videoId;
    }

    /**
     * 获取视频时长（毫秒）
     * @param file
     * @return
     */
    public static Long obtainDuration(MultipartFile file){
        Encoder encoder = new Encoder();
        Long duration = null;
        try {
            // 获取文件类型
            String fileName = file.getContentType();
            // 获取文件后缀
            String pref = fileName.indexOf("/") != -1 ? fileName.substring(fileName.lastIndexOf("/") + 1, fileName.length()) : null;
            String prefix = "." + pref;
            // 用UUID作为文件名 防止被覆盖
            final File excelFile = File.createTempFile(UUID.randomUUID().toString(), prefix);
            // convert to
            file.transferTo(excelFile);
            MultimediaInfo mInfo = encoder.getInfo(excelFile);
            duration = mInfo.getDuration();
            // 删除临时文件
            AliyunUtil.deleteFile(excelFile);
        }catch (Exception e){
            throw new BadRequestException("视频处理失败");
        }

        return duration;
    }

    /**
     * 删除文件
     */
    private static void deleteFile(File... files){
        for (File file : files) {
            if (file.exists()) {
                file.delete();
            }
        }
    }
}
