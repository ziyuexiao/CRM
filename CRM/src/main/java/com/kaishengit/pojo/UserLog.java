package com.kaishengit.pojo;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserLog implements Serializable{

    private Integer id;
    private String logintime;
    private String loginip;
    private Integer userid;

}
