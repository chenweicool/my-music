package com.mymusic.model;

import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author chen
 * @since 2021-03-13
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class SysUserSonglist implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 用户的id
     */
    private Long userId;

    /**
     * 歌单的id
     */
    private Integer songlistId;


}
