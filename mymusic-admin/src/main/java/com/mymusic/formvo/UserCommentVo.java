package com.mymusic.formvo;

import lombok.Data;
import java.io.Serializable;
import java.util.Date;

/**
 * 评论的vo类
 */
@Data
public class UserCommentVo implements Serializable {
    private static final long serialVersionUID = -6174840767808988655L;

    /**
     * 评论的id
     */
    private Long id;
    /**
     * 用户名
     */
    private  String userName;

   /*用户的头像*/
    private String avatar;

    /*评论创建时间*/
    private Date  createTime;

    /*评论的内容*/
    private String content;

    /*点赞的内容*/
    private Integer likeNum;

    /*歌曲名*/
    private  String songName;

    /**
     * 评论的状态 0 待审核  1 审核通过 2  审核未通过
     */
    private Integer commentStatus;
}
