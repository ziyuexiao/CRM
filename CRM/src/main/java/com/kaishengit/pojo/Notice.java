package com.kaishengit.pojo;

import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Created by lenovo on 2017/3/18.
 */
@Data
public class Notice implements Serializable {
    private Integer id;
    private Integer userid;
    private String title;
    private String context;
    private Timestamp createtime;
    private String realname;
}
