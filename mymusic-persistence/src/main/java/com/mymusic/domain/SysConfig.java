package com.mymusic.domain;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 系统全局配置参数
 * </p>
 * @author chen
 * @since 2021-01-16
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class SysConfig implements Serializable {

    private static final long serialVersionUID=1L;

    private Long id;

    /**
     * 参数名称(中文)
     */
    private String paramName;

    /**
     * 参数编码唯一标识(英文及数字)
     */
    private String paramKey;

    /**
     * 参数值
     */
    private String paramValue;

    /**
     * 参数描述备注
     */
    private String paramDesc;

    /**
     * 创建时间
     */
    private Date createTime;


}
