package com.mymusic.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class SysConfiger implements Serializable {
    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
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
