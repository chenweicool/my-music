package com.mymusic.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Song implements Serializable {

    private static final long serialVersionUID = 4710269820298523215L;

    /*歌曲的id*/
    private Long id;

    /*歌手的id*/
    private Integer singerId;

    /*歌曲的名字*/
    private String name;

    /*歌曲的介绍*/
    private String introduction;

    /*歌曲的创建时间*/
    private Date createTime;

    /*歌曲的更新时间*/
    private Date updateTime;

    /*歌曲的图片*/
    private String pic;

    /*歌曲的地址*/
    private String url;

    /**/
    private String lyric;
}