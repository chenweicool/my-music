package com.mymusic.common.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class StatisticsVo implements Serializable {

    private static final long serialVersionUID = 117613799224940783L;
    /*属性的名字*/
    private String name;

    /*属性的值信息 默认是0*/
    private Long  value = 0L;
}
