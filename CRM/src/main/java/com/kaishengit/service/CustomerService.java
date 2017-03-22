package com.kaishengit.service;

import com.kaishengit.mapper.CustomerMapper;
import com.kaishengit.pojo.Customer;
import com.kaishengit.util.ShiroUtil;
<<<<<<< HEAD
import com.kaishengit.util.Strings;
=======
>>>>>>> 96473e52ed04370823477e91a7d6e89f010cdda5
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
<<<<<<< HEAD
import java.util.Map;

=======

/**
 * Created by lenovo on 2017/3/22.
 */
>>>>>>> 96473e52ed04370823477e91a7d6e89f010cdda5
@Service
public class CustomerService {
    @Autowired
    private CustomerMapper customerMapper;
<<<<<<< HEAD

    /**
     * 查询所有公司
     *
     * @return
     */
    public List<Customer> findAllCompany() {
        return customerMapper.findAllCompany(Customer.CUSTOMER_TYPE_COMPANY);
    }

    /**
     * 查询所有
     *
     * @return
     */
    public List<Customer> findAll() {
        return customerMapper.findAll();
    }

    /**
     * 根据参数查询
     *
     * @param params
     * @return
     */
    public List<Customer> findByParam(Map<String, Object> params) {
        //获取当前用户的id
        if (ShiroUtil.isEmployee()) {
            params.put("userid", ShiroUtil.getCurrentUserID());
        }
        return customerMapper.findByParam(params);
    }

    /**
     * 一共多少条数据
     * @return
     */
    public Long count() {
        return customerMapper.count();
    }

    public Long countByParam(Map<String, Object> params) {
        //获取当前用户的id
        if (ShiroUtil.isEmployee()) {
            params.put("userid", ShiroUtil.getCurrentUserID());
        }
        return customerMapper.countByParam(params);
    }

    /**
     * 保存用户
     * @param customer
     */
    public void saveCustomer(Customer customer) {
        //判断是否有所属的公司
        if(customer.getCompanyid() != null){
            //根据id查找对应的公司，然后获取公司名字赋值到customer
            Customer company = customerMapper.findById(customer.getCompanyid());
            customer.setCompanyname(company.getCompanyname());
        }
        customer.setId(ShiroUtil.getCurrentUserID());
        customer.setPinyin(Strings.toPinyiin(customer.getName()));
        customerMapper.save(customer);
    }

    /**
     * 删除用户
     * @param id 用户id
     */

    public void delCustomer(Integer id) {

        Customer customer = customerMapper.findById(id);
        if(customer != null){
            //判断删除的是否是公司，查找是否有关联公司，如果用把公司id设置为空
            if(customer.getType().equals(Customer.CUSTOMER_TYPE_COMPANY)){

                List<Customer> customerList = customerMapper.findAllCompany(String.valueOf(id));
                for(Customer cust : customerList){
                    cust.setCompanyname(null);
                    cust.setCompanyid(null);

                    //更新数据库
                    customerMapper.update(cust);
                }
            }
        }
        customerMapper.customeDel(id);
=======
    /**
     * 展示该用户下的客户
     * @return
     */
    public List<Customer> findAllCustomerByUserid(Integer userid) {
        return customerMapper.findAll(userid);
>>>>>>> 96473e52ed04370823477e91a7d6e89f010cdda5
    }
}
