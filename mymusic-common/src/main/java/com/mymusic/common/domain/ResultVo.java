package com.mymusic.common.domain;

import lombok.Data;

import java.io.Serializable;

@Data
public class ResultVo implements Serializable {
    private static final long serialVersionUID = 784335580622133189L;
    private  String message;
    private  String  result;
}
