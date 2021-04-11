package com.mymusic.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * 评论的回复
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "`user_reply_comment`")
public class UserReplyComment implements Serializable {

    private static final long serialVersionUID = -6072021768908026384L;
    /*评论的id*/
    @Id
    @Column(name = "`id`")
    @GeneratedValue(generator= "JDBC")
    private Long Id;

    /*用户的id信息*/
    @Column(name = "`user_id`")
    private Long userId;

    /*用户的id信息*/
    @Column(name = "`user_name`")
    private String  userName;

    /*用户头像*/
    @Column(name = "`avatar`")
    private String avatar;

    /*被回复用户id信息*/
    @Column(name = "`reply_user_id`")
    private Long replyUserId;

    /*被回复的用户名*/
    @Column(name = "`reply_user_name`")
    private String replyUserName;

    /*回复的内容*/
    @Column(name = "`content`")
    private String content;

    /*评论内容的状态 0 -- 待审核  1-- 审核通过  2  审核未通过*/
    @Column(name = "`comment_status`")
    private int  commentStatus;

    /*点赞数*/
    @Column(name = "`like_num`")
    private int likeNum;

    /*评论的创建时间*/
    @Column(name = "`create_time`")
    private Date createTime;

    /**
     * 更新时间
     */
    @Column(name = "`update_time`")
    private Date updateTime;
}
