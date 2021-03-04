package com.mymusic.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

    /**
     * 管理员
     */
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public class Admin  implements Serializable {

        // 将光标放在类名上，使用alter和enter键进行生成
        private static final long serialVersionUID = -2742389284138243842L;

        /*主键*/
        private Integer id;

        /*用户名*/
        private String name;

        /*密码*/
         private String password;
    }
