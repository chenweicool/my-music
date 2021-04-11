package com.mymusic.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class UserCommentConsumer implements Serializable {
    private static final long serialVersionUID = -3771333927756663378L;

    /**
     * 查询歌曲的id (删除的时候有用)
     */
    private Long id;
    /**
     * 用户名
     */
    private  String userName;

    /*用户的头像*/
    private String avatar;

    /*评论创建时间*/
    private Date createTime;

    /*评论的内容*/
    private String content;

    /*点赞的内容*/
    private Integer likeNum;

    /*歌曲名*/
    private  String name;

    /*审核的状态*/
    private  Integer commentStatus;
}
