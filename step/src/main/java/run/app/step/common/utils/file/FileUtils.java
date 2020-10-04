package run.app.step.common.utils.file;

import run.app.step.common.exception.NotFoundException;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.URLEncoder;

/**
 * 文件处理工具类
 *
 * @author lingSong
 * @date 2020/9/6 15:31
 */
public class FileUtils {
    public static String FILENAME_PATTERN = "[a-zA-Z0-9_\\-\\|\\.\\u4e00-\\u9fa5]+";


    /**
     * 文件名称验证
     * 正则表达式
     *
     * @param filename 文件名称
     * @return true 正常 false 非法
     */
    public static boolean isValidFilename(String filename){
        return filename.matches(FILENAME_PATTERN);
    }


    /**
     * 下文文件名重新编码
     *
     * @param request
     * @param fileName
     * @return
     * @throws UnsupportedEncodingException
     */
    public static String setFileDownloadHeader(HttpServletRequest request, String fileName)
            throws UnsupportedEncodingException {
        // 获取浏览器代理商
        final String agent = request.getHeader("USER-AGENT");
        String filename  = fileName;

        if(agent.contains("MSIE")){
            // IE
            filename = URLEncoder.encode(filename, "utf-8");
            filename = filename.replace("+", " ");
        }else if(agent.contains("Firefox")){
            // huohu
            filename = new String(fileName.getBytes(), "ISO8859-1");
        }else if (agent.contains("Chrome")){
            // google
            filename = URLEncoder.encode(filename, "utf-8");
        }else{
            // others
            filename = URLEncoder.encode(filename, "utf-8");
        }
        return filename;
    }


    /**
     *  输出指定文件的byte数组
     * @param filePath
     * @param os
     * @throws IOException
     */
    public static void writeBytes(String filePath, OutputStream os) throws IOException {
        FileInputStream fis = null;

        try{
            File file = new File(filePath);
            if(!file.exists()){
                throw new NotFoundException("文件下载路径" + filePath + "没有找到该路径");
            }
            fis = new FileInputStream(file);
            byte[] bytes = new byte[1024];
            int length;
            while ((length = fis.read(bytes)) > 0){
                os.write(bytes, 0, length);
            }

        }catch (Exception e){
            throw e;
        }finally {
            if(os != null){
                os.close();
            }
            if(fis != null){
                fis.close();
            }
        }
    }

    /**
     * 删除文件
     *
     * @param filePath 文件
     * @return
     */
    public static boolean deleteFile(String filePath){
        boolean flag = false;
        File file = new File(filePath);
        // 路径为文件且不为空则进行删除
        if (file.isFile() && file.exists()){
            file.delete();
            flag = true;
        }
        return flag;
    }

}
