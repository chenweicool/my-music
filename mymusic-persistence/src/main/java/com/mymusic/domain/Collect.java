package com.mymusic.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 用户的收藏歌单
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Collect {
    /*歌曲的id*/
    private Integer id;

    /*用户的id信息*/
    private Integer userId;

    /*歌曲的类型*/
    private Integer type;

    /*歌曲的id*/
    private Integer songId;

    /*歌单的列表的id*/
    private Integer SongListId;

    /*歌单的创建时间*/
    private Date createTime;
}
