package com.mymusic.domain;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Data
@ConfigurationProperties(prefix = "file")
public class MyFile {

    /*上传的本地的路径*/
    private String uploadFolder;

    /* 上传的token*/
    private String  Access_Key;

    /* 上传的密钥*/
    private String  Secret_Key;

}
