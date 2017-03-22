package com.kaishengit.pojo;

import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;

@Data
public class Customer implements Serializable {

    public static final String CUSTOMER_TYPE_PERSON = "person";
    public static final String CUSTOMER_TYPE_COMPANY = "company";


    private Integer id;
    private Integer userid;
    private String name;
    private String tel;
    private String weixin;
    private String address;
    private String email;
    private Timestamp timestamp;
    private String pinyin;
    private String companyname;
    private Integer companyid;
    private String level;
    private String type;
}
