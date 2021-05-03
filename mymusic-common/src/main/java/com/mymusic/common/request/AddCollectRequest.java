package com.mymusic.common.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;

/**
 * 增加用户收藏的歌单
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddCollectRequest implements Serializable {
    private static final long serialVersionUID = 2749696849549338576L;
    /*用户的id的信息*/
    private Long userId;

    /*歌单的id的信息*/
    private Integer songListId;

    /*收藏歌曲的id的信息*/
    private Long songId;

}
