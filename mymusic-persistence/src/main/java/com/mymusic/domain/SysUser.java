package com.mymusic.domain;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 用户信息表
 * </p>
 *
 * @author chen
 * @since 2021-01-16
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class SysUser implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 用户的id
     */
    private Long id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 用户的密码
     */
    private String password;

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

     /*用户的组织id*/
    private  Long  orgId;

}
