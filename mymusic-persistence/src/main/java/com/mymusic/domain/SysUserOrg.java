package com.mymusic.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 这是一个包装类
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysUserOrg extends SysUser {

    private String orgName;

}