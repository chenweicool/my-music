package com.mymusic.common.utils;


import com.google.gson.Gson;
import com.mymusic.common.enums.FileConsumerType;
import com.mymusic.common.exception.FileException;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import org.springframework.web.multipart.MultipartFile;
import java.io.ByteArrayInputStream;
import java.io.IOException;
/**
 * 用户上传的工具类
 */
public class FileUtils {

    private FileUtils(){}

    /**
     *  上传文件至七牛云的实现
     * @param avatarFile 前端上传的文件
     * @param accessKey 上传文件的密钥
     * @param secretKey 上传文件密钥
     * @param fileKey 文件key需要根据需要进行设置
     * @return
     */
    public static String  getAvatarPic(MultipartFile avatarFile,String accessKey,String secretKey,String fileKey){

        if(avatarFile.isEmpty()){
              return Constants.DEFAULT_PIC;
         }

        //构造一个带指定 Region 对象的配置类
        Configuration cfg = new Configuration(Region.autoRegion());
        UploadManager uploadManager = new UploadManager(cfg);
        String bucket = Constants.BUCKET;

    /*    // 设置文件的上传名
        String fileName = System.currentTimeMillis()+avatarFile.getOriginalFilename();
        String key = Constants.PIC+fileName;*/
        try {
            byte[] uploadBytes = avatarFile.getBytes();
            ByteArrayInputStream byteInputStream=new ByteArrayInputStream(uploadBytes);
            Auth auth = Auth.create(accessKey, secretKey);
            String upToken = auth.uploadToken(bucket);
            try {
                Response response = uploadManager.put(byteInputStream,fileKey,upToken,null, null);
                //解析上传成功的结果
                DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
                System.out.println(putRet.key);
                return  putRet.key;
            } catch (QiniuException ex) {
                Response r = ex.response;
                System.err.println(r.toString());
                try {
                    System.err.println(r.bodyString());
                } catch (QiniuException ex2) {
                    //ignore
                }
            }
        } catch (IOException ex) {
            throw new FileException(FileConsumerType.FILE_ERROR);
        }
        return null;
    }
}
