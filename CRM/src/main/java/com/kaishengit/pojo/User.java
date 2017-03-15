package com.kaishengit.pojo;

import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;
@Data
public class User implements Serializable {


    private Integer id;
    private String username;
    private String password;
    private String realname;
    private String weixin;
    private Timestamp createtime;
    private Integer roleid;
    private Role role;
    private Boolean enable;



}
