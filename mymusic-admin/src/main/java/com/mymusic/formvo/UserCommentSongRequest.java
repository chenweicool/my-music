package com.mymusic.formvo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 接受歌曲的评论参数
 */
@Data
public class UserCommentSongRequest implements Serializable {

    private static final long serialVersionUID = 7048748593424289770L;


    /**
     * 用户的id
     */
    private Long userId;

    /**
     * 用户的头像
     */
    private String avatar;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 评论的内容
     */
    private String content;

    /**
     * 是否公开
     */
    private int publicStatus;

    /**
     * 点赞数
     */
    private int likeNum;


    /*歌曲的id信息*/
    private Long songId;
}
