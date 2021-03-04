package com.mymusic.common.exception;

import com.mymusic.common.enums.UserConsumerType;
import lombok.Getter;

/**
 * 用户的异常类
 */
@Getter
public class UserException  extends  RuntimeException{

    private int code;
    private String message;

    public UserException(UserConsumerType consumerType){
          this.code = consumerType.getCode();
           this.message = consumerType.getMessage();
    }

    public UserException(UserConsumerType consumerType, String message) {
         this.code = consumerType.getCode();
         this.message = message;
    }

    public UserException(String message) {
        this.message = message;
    }
}
