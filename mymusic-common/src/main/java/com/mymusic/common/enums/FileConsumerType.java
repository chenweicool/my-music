package com.mymusic.common.enums;

import lombok.Getter;

/**
 * 文件的异常类
 */
@Getter
public enum FileConsumerType {
    FILE_ERROR(1,"上传文件错误"),
    File_NOT_EIISTS(0,"该文件不存在");
    private Integer code;
    private String  message;
    FileConsumerType(){}

    FileConsumerType(Integer code, String msg) {
        this.code = code;
        this.message = msg;
    }

}
