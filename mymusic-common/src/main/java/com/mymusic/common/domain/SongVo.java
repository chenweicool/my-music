package com.mymusic.common.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 歌曲的分页的映射类
 */
@Data
public class SongVo implements Serializable {
    private static final long serialVersionUID = -558522747774589208L;

    private Long id;

    private String songName;

    private String singerName;

    private  String introduction;

    private String lyric;

    private String pic;

    private String url;

    private Date createTime;
}
