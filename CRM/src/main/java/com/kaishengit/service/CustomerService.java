package com.kaishengit.service;

import com.kaishengit.mapper.CustomerMapper;
import com.kaishengit.pojo.Customer;
import com.kaishengit.util.ShiroUtil;
import com.kaishengit.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import java.util.Map;

@Service
public class CustomerService {
    @Autowired
    private CustomerMapper customerMapper;


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
     *
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
     *
     * @param customer
     */
    public void saveCustomer(Customer customer) {
        //判断是否有所属的公司
        if (customer.getCompanyid() != null) {
            //根据id查找对应的公司，然后获取公司名字赋值到customer
            Customer company = customerMapper.findById(customer.getCompanyid());
            customer.setCompanyname(company.getName());
        }
        customer.setUserid(ShiroUtil.getCurrentUserID());
        customer.setPinyin(Strings.toPinyiin(customer.getName()));
        customerMapper.save(customer);
    }

    /**
     * 删除用户
     *
     * @param id 用户id
     */

    @Transactional
    public void delCustomer(Integer id) {

        Customer customer = customerMapper.findById(id);
        if (customer != null) {
            //判断删除的是否是公司，查找是否有关联公司，如果用把公司id设置为空
            if (customer.getType().equals(Customer.CUSTOMER_TYPE_COMPANY)) {

                List<Customer> customerList = customerMapper.findAllCompany(String.valueOf(id));
                for (Customer cust : customerList) {
                    cust.setCompanyname(null);
                    cust.setCompanyid(null);

                    //更新数据库
                    customerMapper.update(cust);
                }
            }
        }
        customerMapper.customeDel(id);
    }

    /**
     * 根据id查找对应的客户
     *
     * @param id
     * @return
     */
    public Customer findCustomerById(Integer id) {
        return customerMapper.findById(id);
    }

    /**
     * 修改用户
     *
     * @param customer
     */

    @Transactional
    public void editCustomer(Customer customer) {
        //判断如果是公司
        if (customer.getType().equals(Customer.CUSTOMER_TYPE_COMPANY)) {
            //找到关联的用户,修改
            List<Customer> customerList = customerMapper.findByCompanyId(customer.getId());
            for (Customer customer1 : customerList) {
                customer1.setCompanyname(customer.getName());
                customer1.setCompanyid(customer.getCompanyid());
                customerMapper.update(customer1);
            }
        } else {
            //是个人
            if (customer.getCompanyid() != null) {
                Customer company = customerMapper.findById(customer.getCompanyid());
                company.setCompanyname(company.getName());
            }
        }
        customer.setPinyin(Strings.toPinyiin(customer.getName()));
        customerMapper.update(customer);
    }

    /**
     * 根据公司id获取公司的客户
     * @param id
     * @return
     */
    public List<Customer> findCompanyCustomerById(Integer id) {
        return customerMapper.findByCompanyId(id);
    }

    /**
     * 公开用户
     * @param customer
     */
    public void openCustomer(Customer customer) {
        customer.setUserid(null);
        customerMapper.update(customer);
    }

    /**
     * 转义用户
     * @param customer
     * @param userid
     */

    public void moveCust(Customer customer, Integer userid) {
        customer.setUserid(userid);
        customerMapper.update(customer);
    }
}
