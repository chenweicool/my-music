package com.mymusic.common.enums;

import lombok.Getter;

@Getter
public enum  UserConsumerType {

    USER_NOT_EXIST(1,"该用户不存在"),
    PASSWORD_ERROR(2, "用户名的密码错误，请重新输入");

    private int code;
    private String message;

     UserConsumerType(){}
     UserConsumerType(int code, String message) {
       this.code = code;
       this.message = message;
    }
}
