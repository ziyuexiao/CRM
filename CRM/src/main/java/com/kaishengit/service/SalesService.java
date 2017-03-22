package com.kaishengit.service;

import com.google.common.collect.Maps;
import com.kaishengit.mapper.CustomerMapper;
import com.kaishengit.mapper.SalesMapper;
import com.kaishengit.pojo.Sales;
import com.kaishengit.util.ShiroUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by lenovo on 2017/3/22.
 */
@Service
public class SalesService {
    @Autowired
    private SalesMapper salesMapper;
    @Autowired
    private CustomerMapper customerMapper;


    /**
     * datatables方式查找销售机会
     * @param params
     * @return
     */
    public List<Sales> findByParam(Map<String, Object> params) {

        params.put("userid",ShiroUtil.getCurrentUserID());

        return salesMapper.findByParam(params);
    }

    /**
     * 销售机会的数量
     * @return
     */
    public Long count() {
        Map<String,Object> params = Maps.newHashMap();

        params.put("userid",ShiroUtil.getCurrentUserID());

        return salesMapper.countByParam(params);
    }

    /**
     * 有搜索条件的销售机会的数量
     * @param params
     * @return
     */
    public Long countByParam(Map<String, Object> params) {

            params.put("userid",ShiroUtil.getCurrentUserID());
        return salesMapper.countByParam(params);
    }

    /**
     * 新增机会
     * @param sales
     */
    public void saveSales(Sales sales) {
        sales.setUserid(ShiroUtil.getCurrentUserID());
        sales.setUsername(ShiroUtil.getCurrentRealName());
        sales.setCustname(customerMapper.findById(sales.getCustid()).getName());

        salesMapper.save(sales);
    }
}
