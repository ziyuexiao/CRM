package com.kaishengit.mapper;

import com.kaishengit.pojo.Customer;

import java.util.List;

/**
 * Created by lenovo on 2017/3/22.
 */
public interface CustomerMapper {
    List<Customer> findAll(Integer userid);


    Customer findById(Integer custid);
}
