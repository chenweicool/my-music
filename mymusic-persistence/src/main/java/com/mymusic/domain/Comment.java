package com.mymusic.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 用户评论的实体类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Comment {
    /*评论的id*/
    private Integer id;

    /*用户的id信息*/
    private Integer userId;

    /*评论的歌曲的id*/
    private Integer songId;

    /*歌单的id值*/
    private Integer songListId;

    /*评论的内容*/
    private String content;

    /*评论的创建时间*/
    private Date createTime;

    /*歌曲的类型*/
    private Integer type;

    private Integer up;
}
