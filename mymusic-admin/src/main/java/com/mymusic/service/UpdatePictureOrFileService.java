package com.mymusic.service;

import com.mymusic.common.config.CosProperties;
import com.mymusic.common.utils.FileUtils;
import com.mymusic.common.utils.ParameterCheckUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

/**
 * 更新和歌曲文件
 */
@Service
public class UpdatePictureOrFileService {

    @Resource
    private CosProperties cosProperties;

    /**
     * 更新图片使用
     * @param file 文件信息
     * @param fileKey
     * @return
     */
    public  String  updatePictureOrFile(MultipartFile file,String fileKey){
        ParameterCheckUtils.checkParamIsBlank(file, fileKey);
        String fileTag = FileUtils.upLoadPicture(file, cosProperties, fileKey);
        return  fileTag;
    }
}
