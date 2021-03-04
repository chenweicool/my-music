package com.mymusic.domain;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/*歌单中的歌曲的信息实体类*/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ListSong implements Serializable {

    private static final long serialVersionUID = 9069860265650061772L;

    private Integer id;

    private Integer songId;

    private Integer songListId;
}
