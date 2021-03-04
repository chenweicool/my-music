package com.mymusic.common.exception;

import com.mymusic.common.enums.SingerConsumerType;
import lombok.Getter;

/**
 * 歌手信息的异常类
 */
@Getter
public class SingerException extends RuntimeException {
    /*异常码*/
    private int code;

    /*异常信息*/
    private String message;

    public SingerException(SingerConsumerType consumerType){
        this.code = consumerType.getCode();
        this.message = consumerType.getMessage();
    }

    public SingerException(SingerConsumerType consumerType, String message) {
        this.code = consumerType.getCode();
        this.message = message;
    }

    public SingerException(String message) {
        this.message = message;
    }
}
