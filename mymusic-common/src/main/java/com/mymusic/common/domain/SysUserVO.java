package com.mymusic.common.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class SysUserVO implements Serializable {
    private static final long serialVersionUID = 376151157855536062L;
    /**
     * 用户的id
     */
    private Long id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 性别
     */
    private Integer sex;

    /**
     * 用户的电话
     */
    private String phone;

    /**
     * 电子邮件
     */
    private String email;

    /**
     * 生日
     */
    private Date birth;

    /**
     * 用户的介绍
     */
    private String introduction;

    /**
     * 区域
     */
    private String location;

    /**
     * 头像
     */
    private String avator;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 用户的状态
     */
    private Boolean enabled;
}
