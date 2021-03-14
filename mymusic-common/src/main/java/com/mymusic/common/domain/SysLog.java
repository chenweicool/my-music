package com.mymusic.common.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;
import java.util.Date;

/**
 * 用户的操作日志
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SysLog implements Serializable {

    private static final long serialVersionUID = -3117122445435750868L;
    // 操作日志的id
    private Long id;

    // 操作的用户的名称
    private String userName;

    // 获取具体的操作信息
    private String operation;

    // 操作的方法名
    private String method;

    // 方法的参数信息
    private String params;

    // 用户的ip地址
    private String ip;

    // 操作时间
    private Date createTime;

    // 用户的歌曲的Id
    private long SongId;
}
