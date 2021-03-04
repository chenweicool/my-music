package com.mymusic.domain;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 数据字典表
 * </p>
 *
 * @author chen
 * @since 2021-01-16
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class SysDict implements Serializable {

    private static final long serialVersionUID=1L;

    private Long id;

    /**
     * 分组名称
     */
    private String groupName;

    /**
     * 分组编码
     */
    private String groupCode;

    /**
     * 字典项名称
     */
    private String itemName;

    /**
     * 字典项Value
     */
    private String itemValue;

    /**
     * 字典项描述
     */
    private String itemDesc;

    /**
     * 字典项创建时间
     */
    private Date createTime;


}
