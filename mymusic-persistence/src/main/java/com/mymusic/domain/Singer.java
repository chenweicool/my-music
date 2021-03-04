package com.mymusic.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 歌手的信息
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Singer implements Serializable {

    private static final long serialVersionUID = -4907149036248193458L;

    /*歌手的id*/
    private Integer id;

    /*歌手的名字*/
    private String name;

    /*用户的性别*/
    private Integer sex;

    /*头像*/
    private String pic;

    /*生日*/
    private Date birth;

    /*区域*/
    private String location;

    /*简介*/
    private String introduction;
}
