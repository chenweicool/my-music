package com.mymusic.common.enums;

import lombok.Getter;

@Getter
public   enum   SingerConsumerType {
    SINGER_NOT_EXIST(1, "该歌手不存在");
    private Integer code;
    private String message;

    SingerConsumerType(){}

    SingerConsumerType(int code, String message) {
         this.code = code;
         this.message = message;
    }
}
