package com.mymusic.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/*歌单的实体类*/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SongList implements Serializable {
    private static final long serialVersionUID = -1375007880362974088L;

    private Integer id;

    private String title;

    private String pic;

    private String style;

    private String introduction;

    private Integer type;

    private String uuid;

    //todo 待实现的功能
    private Date createTime;

    private Date updateTime;
//
    private Long userId;
//
//    private String userName;

}