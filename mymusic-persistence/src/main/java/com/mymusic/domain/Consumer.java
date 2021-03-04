package com.mymusic.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户的信息类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Consumer implements Serializable {

    private static final long serialVersionUID = 806686491887880014L;

    /*用户的id*/
    private Integer id;

    /*用户名的信息*/
    private String username;

    /*用户的密码*/
    private String password;

    /*用户的性别*/
    private Integer sex;

    /*用户的电话*/
    private String phoneNum;

    /*用户的邮箱*/
    private String email;

    /*用户的生日*/
    private Date birth;

    /*用户的自我介绍*/
    private String introduction;

    /*用户的所属地域*/
    private String location;

    /*用户的头像*/
    private String avator;

    /*用户的创建时间*/
    private Date createTime;

    /*用户信息的更新时间*/
    private Date updateTime;

    /**
     * 组织id
     */
    private Long orgId;

    /**
     * 0无效用户，1是有效用户
     */
    private Boolean enabled;
}