package com.mymusic.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 系统组织结构表
 * </p>
 *
 * @author chen
 * @since 2021-01-16
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class SysOrg implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)

    private Long id;

    /**
     * 上级组织编码
     */
    private Long orgPid;

    /**
     * 所有的父节点id
     */
    private String orgPids;

    /**
     * 0:不是叶子节点，1:是叶子节点
     */
    private Boolean isLeaf;

    /**
     * 组织名
     */
    private String orgName;

    /**
     * 地址
     */
    private String address;

    /**
     * 电话
     */
    private String phone;

    /**
     * 邮件
     */
    private String email;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 组织层级
     */
    private Integer level;

    /**
     * 是否禁用，0:启用(否）,1:禁用(是)
     */
    private Boolean status;



}
