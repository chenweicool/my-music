package com.mymusic.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 歌曲的评分的实体类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Rank {
    /*评分的id*/
    private Long id;

    /*歌单的列表id*/
    private Long songListId;

    /*用户的id*/
    private Long consumerId;

    /*评分的内容*/
    private Integer score;

}
