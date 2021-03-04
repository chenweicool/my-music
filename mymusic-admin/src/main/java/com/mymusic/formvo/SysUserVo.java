package com.mymusic.formvo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * 传递给前端的对象信息
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class SysUserVo  implements Serializable {

    private static final long serialVersionUID = -8411771768342279424L;

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
     * 用户的状态
     */
    private Boolean enabled;

}
