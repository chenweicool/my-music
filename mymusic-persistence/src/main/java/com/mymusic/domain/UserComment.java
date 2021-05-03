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
 * 用户评论
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "`user_comment`")
public class UserComment implements Serializable {

    private static final long serialVersionUID = -1530623204807964122L;
    /*评论的id*/
    @Id
    @Column(name = "`id`")
    @GeneratedValue(generator= "JDBC")
    private Long Id;

    /*用户的id信息*/
    @Column(name = "`user_id`")
    private Long userId;

    /*用户头像*/
    @Column(name = "`avatar`")
    private String avatar;

    /*歌曲的Id*/
    @Column(name = "`song_id`")
    private Long songId;


    /*用户名*/
    @Column(name = "`user_name`")
    private String userName;

    /*评论的内容*/
    @Column(name = "`content`")
    private String content;

    /*评论内容的状态 0 -- 待审核  1-- 审核通过  2  审核未通过*/
    @Column(name = "`comment_status`")
    private int  commentStatus;

    /**
     * 是否公开 0--表示公开，1 表示不公开  默认是公开的
     */
    @Column(name = "`public_status`")
    private int publicStatus;

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

    /**
     * 评论的唯一标识
     */
    private  String uuid;

    private Integer type;
}
