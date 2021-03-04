package com.mymusic.common.enums;

import lombok.Getter;

/**
 * 歌曲的枚举类型
 */
@Getter
public enum  SongConsumerType {
    SONG_NOT_EXIST(1, "该歌曲不存在"),
    SONGLIST_NOT_EXIST(2, "该歌单不存在"),
    SONGLIST_TITLE_EXIST (3,"没有属于该歌单标题的歌曲"),
    SONGLIST_STYLE_EXIST (3,"没有属于该歌单的类型的歌曲");
    private Integer code;
    private String message;

    SongConsumerType(){}

    SongConsumerType(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
