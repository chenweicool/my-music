package com.mymusic.domain;

import lombok.Data;

import java.io.Serializable;

@Data
public class SongComment implements Serializable {
    private static final long serialVersionUID = -3547351083104950191L;

    private long songId;

    private long commentId;
}
