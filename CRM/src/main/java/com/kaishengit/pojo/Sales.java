package com.kaishengit.pojo;

import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Created by lenovo on 2017/3/22.
 */
@Data
public class Sales implements Serializable {
    private Integer id;
    private Integer custid;
    private Integer userid;
    private String name;
    private Float price;
    private String custname;
    private String progress;
    private Timestamp createtime;
    private String lasttime;
    private String username;
    private String successtime;
}
