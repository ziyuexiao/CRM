package com.kaishengit.service;

import com.kaishengit.mapper.CustomerMapper;
import com.kaishengit.pojo.Customer;
import com.kaishengit.util.ShiroUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by lenovo on 2017/3/22.
 */
@Service
public class CustomerService {
    @Autowired
    private CustomerMapper customerMapper;
    /**
     * 展示该用户下的客户
     * @return
     */
    public List<Customer> findAllCustomerByUserid(Integer userid) {
        return customerMapper.findAll(userid);
    }
}
