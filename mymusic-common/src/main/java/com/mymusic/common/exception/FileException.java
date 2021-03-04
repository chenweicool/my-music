package com.mymusic.common.exception;

import com.mymusic.common.enums.FileConsumerType;

public class FileException extends RuntimeException {

    /*异常码*/
    private int code;

    /*异常信息*/
    private String message;

    public FileException(FileConsumerType consumerType){
        this.code = consumerType.getCode();
        this.message = consumerType.getMessage();
    }

    public FileException(String message){
        this.message = message;
    }
    public FileException(FileConsumerType consumerType, String message) {
        this.code = consumerType.getCode();
        this.message = message;
    }
}
