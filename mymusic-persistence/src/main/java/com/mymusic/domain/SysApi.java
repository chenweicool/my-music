package com.mymusic.domain;

import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 系统Http接口表，配合sys_role_api控制接口访问权限
 * </p>
 *
 * @author chen
 * @since 2021-01-16
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class SysApi implements Serializable {

    private static final long serialVersionUID=1L;

    private Long id;

    /**
     * 接口父ID(即接口分组)
     */
    private Long apiPid;

    /**
     * 当前接口的所有上级id(即所有上级分组)
     */
    private String apiPids;

    /**
     * 0:不是叶子节点，1:是叶子节点
     */
    private Boolean isLeaf;

    /**
     * 接口名称
     */
    private String apiName;

    /**
     * 跳转URL
     */
    private String url;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 层级，1：接口分组，2：接口
     */
    private Integer level;

    /**
     * 是否禁用，0:启用(否）,1:禁用(是)
     */
    private Boolean status;


}
