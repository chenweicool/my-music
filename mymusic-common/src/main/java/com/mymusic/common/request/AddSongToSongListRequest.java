package com.mymusic.common.request;

import lombok.Data;
import java.util.List;

/**
 * 添加用户歌曲到指定的歌单
 */
@Data
public class AddSongToSongListRequest {
    /*歌曲id*/
    private Long songId;

    /*歌单的id集合*/
    private List<Integer> songListIds;
}
