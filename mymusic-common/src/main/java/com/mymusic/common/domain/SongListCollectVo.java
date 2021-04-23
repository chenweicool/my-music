package com.mymusic.common.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class SongListCollectVo implements Serializable {
    private static final long serialVersionUID = 1391321822318507281L;
    private Integer id;

    private String title;

    private String pic;

    private String style;

    private String introduction;

    /*收藏时间*/
    private Date updateTime;
}
