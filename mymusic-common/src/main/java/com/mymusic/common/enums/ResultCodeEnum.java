package com.mymusic.common.enums;

import lombok.Getter;
import lombok.ToString;

/**
 * 返回类型的枚举类型
 */
@Getter
@ToString
public enum  ResultCodeEnum {

    // 管理员的相应的枚举类
    SUCCESS(true, 20000, "成功"),
    UNKNOWN_ERROR(false, 20001, "未知错误"),
    USER_NAME_NOT_NULL(false,20002,"用户名不能为空"),

    /*歌曲信息的枚举*/
    SONG_NOT_EXISTS(false, 21001, "该歌曲不存在"),
    SONGID_NOT_NULL(false,21002,"歌曲的id不能为空"),
    COLLECT_NOT_NULL(false, 21003, "收藏的歌曲信息不存在"),
    NO_REPEAT(false, 21004, "该歌曲已经收藏，不能重复收藏"),
    QUERY_NO_EXIST(false,21005,"查询的信息不存在，请重新查询哟"),


    SINGERID_NOT_NULL(false,22001,"歌手的id不能为空"),

    FILE_NOT_NULL(false, 23001, "上传文件不能为空"),
    ;
    /*成功的标志*/
    private Boolean success;

    /*状态码*/
    private Integer code;

    /*返回的具体的信息*/
    private String message;

    ResultCodeEnum(boolean success, Integer code, String message) {
        this.success = success;
        this.code = code;
        this.message = message;
    }
}
