package com.kaishengit.mapper;

import com.kaishengit.pojo.Customer;

import java.util.List;
import java.util.Map;

/**
 * Created by wgs on 2017/3/22.
 */

public interface CustomerMapper {

    List<Customer> findAllCompany(String type);

    List<Customer> findAll();

    List<Customer> findByParam(Map<String, Object> params);

    Long count();

    Long countByParam(Map<String, Object> params);

    Customer findById(Integer companyid);

    void save(Customer customer);

    void update(Customer cust);

    void customeDel(Integer id);



    List<Customer> findAll(Integer userid);




}
