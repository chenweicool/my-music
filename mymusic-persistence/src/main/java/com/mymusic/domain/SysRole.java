package com.mymusic.domain;


import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 系统角色表
 * </p>
 *
 * @author chen
 * @since 2021-01-16
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class SysRole implements Serializable {

    private static final long serialVersionUID=1L;

    private Long id;

    /**
     * 角色名称(汉字)
     */
    private String roleName;

    /**
     * 角色描述
     */
    private String roleDesc;

    /**
     * 角色的英文code.如：ADMIN
     */
    private String roleCode;

    /**
     * 角色顺序
     */
    private Integer sort;

    /**
     * 是否禁用，0:启用(否）,1:禁用(是)
     */
    private Boolean status;


}
