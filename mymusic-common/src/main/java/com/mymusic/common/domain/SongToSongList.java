package com.mymusic.common.domain;

import lombok.Data;

import java.io.Serializable;

@Data
public class SongToSongList implements Serializable {
    private static final long serialVersionUID = -1583459376734463781L;

    private Integer id;

    private Integer songlistId;

    private  Long  songId;

    // 区分不同的歌单内容的。
    private Integer type = 0;
}
