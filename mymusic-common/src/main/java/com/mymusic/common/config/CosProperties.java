package com.mymusic.config;

import lombok.Data;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 腾讯对象存储的配置信息的类
 */
@Data
@ToString
@ConfigurationProperties("cos")
@Configuration
public class CosProperties {

    /*访问地址*/
    private String  hostName;

    /*存储桶的信息*/
    private String  bucketName;

    /*存储的地区的信息*/
    private String   region;

    /*存储密钥id*/
    private  String  secretId;

    /*存储的密钥key*/
    private  String  secretKey;
}
