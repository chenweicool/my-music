package com.mymusic.common.utils;


import com.mymusic.common.config.CosProperties;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.http.HttpMethodName;
import com.qcloud.cos.http.HttpProtocol;
import com.qcloud.cos.model.GeneratePresignedUrlRequest;
import com.qcloud.cos.model.ObjectMetadata;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.model.PutObjectResult;
import com.qcloud.cos.region.Region;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Date;

/**
 * 用户上传的工具类
 */
public class FileUtils {

    private FileUtils(){}

    /**
     *  用户的信息
     * @param pictureFile {@link MultipartFile}
     * @param properties {@link CosProperties}
     * @return 文件的tag的信息
     */
    public static String  upLoadPicture(MultipartFile pictureFile, CosProperties properties,String fileKey){
        if(pictureFile.isEmpty()){
            return Constants.DEFAULT_PIC;
        }

        COSCredentials cred = new BasicCOSCredentials(properties.getSecretId(), properties.getSecretKey());
        com.qcloud.cos.region.Region region = new com.qcloud.cos.region.Region(properties.getRegion());
        ClientConfig clientConfig = new ClientConfig(region);
        // 这里建议设置使用 https 协议
        clientConfig.setHttpProtocol(HttpProtocol.http);
        // 3 生成 cos 客户端。
        COSClient cosClient = new COSClient(cred, clientConfig);
        // 指定要上传的文件
      //  File localFile = new File("D:\\Pictures\\有趣的照片\\2.jpg");
        byte[] uploadBytes = new byte[1024];
        try {
            uploadBytes = pictureFile.getBytes();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ByteArrayInputStream byteInputStream=new ByteArrayInputStream(uploadBytes);

        // 指定文件将要存放的存储桶
   //     String bucketName = Constants.BUCKET_NAME;
         // 指定文件上传到 COS 上的路径，即对象键。例如对象键为folder/picture.jpg，则表示将文件 picture.jpg 上传到 folder 路径下
    //    String key = UUID.randomUUID().toString()+ "2.jpg";
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentEncoding("UTF-8");
        //objectMetadata.setContentLength(500);
        PutObjectRequest putObjectRequest = new PutObjectRequest(properties.getBucketName(), fileKey, byteInputStream,objectMetadata);
        PutObjectResult putObjectResult = cosClient.putObject(putObjectRequest);
        System.out.println(putObjectResult.getETag());
        // 上传成功后，传递回去一个tag
        return putObjectResult.getETag();
    }

    /**
     *  用户的信息
     * @param pictureFile {@link MultipartFile}
     * @param properties {@link CosProperties}
     * @return 文件的tag的信息
     */
    public static String  upLoadFile(MultipartFile pictureFile, CosProperties properties,String fileKey){
        if(pictureFile.isEmpty()){
            return Constants.DEFAULT_PIC;
        }

        COSCredentials cred = new BasicCOSCredentials(properties.getSecretId(), properties.getSecretKey());
        com.qcloud.cos.region.Region region = new com.qcloud.cos.region.Region(properties.getRegion());
        ClientConfig clientConfig = new ClientConfig(region);
        // 这里建议设置使用 https 协议
        clientConfig.setHttpProtocol(HttpProtocol.http);
        // 3 生成 cos 客户端。
        COSClient cosClient = new COSClient(cred, clientConfig);
        // 指定要上传的文件
        //  File localFile = new File("D:\\Pictures\\有趣的照片\\2.jpg");
        byte[] uploadBytes = new byte[1024];
        try {
            uploadBytes = pictureFile.getBytes();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ByteArrayInputStream byteInputStream=new ByteArrayInputStream(uploadBytes);

        // 指定文件将要存放的存储桶
        //     String bucketName = Constants.BUCKET_NAME;
        // 指定文件上传到 COS 上的路径，即对象键。例如对象键为folder/picture.jpg，则表示将文件 picture.jpg 上传到 folder 路径下
        //    String key = UUID.randomUUID().toString()+ "2.jpg";
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentEncoding("UTF-8");
        objectMetadata.setContentLength(uploadBytes.length);
        PutObjectRequest putObjectRequest = new PutObjectRequest(properties.getBucketName(), fileKey, byteInputStream,objectMetadata);
        PutObjectResult putObjectResult = cosClient.putObject(putObjectRequest);
        System.out.println(putObjectResult.getETag());
        // 上传成功后，传递回去一个tag
        return putObjectResult.getETag();
    }

    /**
     * 查询文件是否存在
     * @param fileKey 文件名
     * @param accessKey 用户的密钥
     * @param secretKey 用户的密钥
     * @param desc 访问的url
     * @return 文件的etag的值
     */
    public static boolean isExistByFile(String fileKey,String accessKey,String secretKey,String desc){

        COSCredentials cred = new BasicCOSCredentials(accessKey, secretKey);
        com.qcloud.cos.region.Region region = new com.qcloud.cos.region.Region(Constants.REGION_NANJING);
        ClientConfig clientConfig = new ClientConfig(region);
        // 这里建议设置使用 https 协议
        clientConfig.setHttpProtocol(HttpProtocol.http);
        // 3 生成 cos 客户端。
        COSClient cosClient = new COSClient(cred, clientConfig);
        ObjectMetadata objectMetadata = cosClient.getObjectMetadata(Constants.BUCKET_NAME, fileKey);

        // 如果不为空，就说明文件是存在的
        boolean empty = StringUtils.isEmpty(objectMetadata.getETag());
        return !empty;
    }

    /**
     * 生成歌曲文件的下载链接
     * @param key 文件key
     * @param properties 存储的配置文件
     * @return 文件下载url
     */
    public static String getDownLoadURL(String key,CosProperties properties){
        // 初始化永久密钥信息
        String secretId = properties.getSecretId();
        String secretKey = properties.getSecretKey();
        COSCredentials cred = new BasicCOSCredentials(secretId, secretKey);
        Region region = new Region(Constants.REGION_NANJING);
        ClientConfig clientConfig = new ClientConfig(region);
        // 如果要生成一个使用 https 协议的 URL，则设置此行，推荐设置。
        // clientConfig.setHttpProtocol(HttpProtocol.https);
        // 生成 cos 客户端。
        COSClient cosClient = new COSClient(cred, clientConfig);
        // 存储桶的命名格式为 BucketName-APPID，此处填写的存储桶名称必须为此格式
        String bucketName = Constants.BUCKET_NAME;
        GeneratePresignedUrlRequest req =
                new GeneratePresignedUrlRequest(bucketName, key, HttpMethodName.GET);
        // 设置签名过期时间(可选), 若未进行设置, 则默认使用 ClientConfig 中的签名过期时间(1小时)
        // 这里设置签名在半个小时后过期
        Date expirationDate = new Date(System.currentTimeMillis() + 180L * 60L * 1000L);
        req.setExpiration(expirationDate);
        URL url = cosClient.generatePresignedUrl(req);
       // System.out.println(url.toString());
        cosClient.shutdown();
        return url.toString();
    }


    /**
     *  上传文件至七牛云的实现,使用腾讯云的对象存储来实现
     *   @param avatarFile 前端上传的文件
     * @param accessKey 上传文件的密钥
     * @param secretKey 上传文件密钥
     * @param fileKey 文件key需要根据需要进行设置
     * @return
     */
//    @Deprecated
//    public static String  getAvatarPic(MultipartFile avatarFile,String accessKey,String secretKey,String fileKey){
//
//        if(avatarFile.isEmpty()){
//              return Constants.DEFAULT_PIC;
//         }
//
//        //构造一个带指定 Region 对象的配置类
//        Configuration cfg = new Configuration(Region.autoRegion());
//        UploadManager uploadManager = new UploadManager(cfg);
//        String bucket = Constants.BUCKET;
//
//    /*    // 设置文件的上传名
//        String fileName = System.currentTimeMillis()+avatarFile.getOriginalFilename();
//        String key = Constants.PIC+fileName;*/
//        try {
//            byte[] uploadBytes = avatarFile.getBytes();
//            ByteArrayInputStream byteInputStream=new ByteArrayInputStream(uploadBytes);
//            Auth auth = Auth.create(accessKey, secretKey);
//            String upToken = auth.uploadToken(bucket);
//            try {
//                Response response = uploadManager.put(byteInputStream,fileKey,upToken,null, null);
//                //解析上传成功的结果
//                DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
//                System.out.println(putRet.key);
//                return  putRet.key;
//            } catch (QiniuException ex) {
//                Response r = ex.response;
//                System.err.println(r.toString());
//                try {
//                    System.err.println(r.bodyString());
//                } catch (QiniuException ex2) {
//                    //ignore
//                }
//            }
//        } catch (IOException ex) {
//            throw new FileException(FileConsumerType.FILE_ERROR);
//        }
//        return null;
//    }



}
