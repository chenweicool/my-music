package com.mymusic.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户播放历史的以及播放次数的实现
 */
@Data
@Accessors(chain = true)
public class SongPlayCount implements Serializable {
    private static final long serialVersionUID = -6113572949708606328L;

    private Long id;

    /*用户id*/
    private Long userId;

    /*歌曲id*/
    private Long songId;

    /*播放的次数*/
    private Integer playCount;

    /*下载次数*/
    private Integer  downCount;

    /*创建时间*/
    private Date createTime;

    /*更新时间*/
    private Date updateTime;
}
