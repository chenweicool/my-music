package com.mymusic.common.exception;

import com.mymusic.common.enums.SongConsumerType;

public class SongException extends  RuntimeException{
    /*异常码*/
    private int code;

    /*异常信息*/
    private String message;

    public SongException(SongConsumerType consumerType){
        this.code = consumerType.getCode();
        this.message = consumerType.getMessage();
    }

    public SongException(SongConsumerType consumerType, String message) {
        this.code = consumerType.getCode();
        this.message = message;
    }

    public SongException(String message) {
        this.message = message;
    }
}
