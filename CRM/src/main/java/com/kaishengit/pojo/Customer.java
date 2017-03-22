package com.kaishengit.pojo;

import lombok.Data;

<<<<<<< HEAD
import java.io.Serializable;
import java.sql.Timestamp;

@Data
public class Customer implements Serializable {

=======
import java.sql.Timestamp;

/**
 * Created by lenovo on 2017/3/22.
 */
@Data
public class Customer {
>>>>>>> 96473e52ed04370823477e91a7d6e89f010cdda5
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
