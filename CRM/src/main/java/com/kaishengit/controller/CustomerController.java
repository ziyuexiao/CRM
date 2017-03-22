package com.kaishengit.controller;

import com.google.common.collect.Maps;
import com.kaishengit.dto.AjaxResult;
import com.kaishengit.dto.DataTablesResult;
import com.kaishengit.pojo.Customer;
import com.kaishengit.service.CustomerService;
import javafx.geometry.Pos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wgs on 2017/3/22.
 */
@Controller
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @RequestMapping(method = RequestMethod.GET)
    public String list(Model model) {
        List<Customer> customerList = customerService.findAllCompany();
        model.addAttribute("customerList", customerList);
        return "customer/list";
    }

    @RequestMapping(value = "/load", method = RequestMethod.GET)
    @ResponseBody
    public DataTablesResult<Customer> load(HttpServletRequest request) {

        //获取dataTable数据，封装，发送到客户端
        String draw = request.getParameter("draw");
        String start = request.getParameter("start");
        String length = request.getParameter("length");
        String keyword = request.getParameter("search[value]");

        //封装参数
        Map<String, Object> params = Maps.newHashMap();
        params.put("start", start);
        params.put("length", length);
        params.put("keyword", keyword);

        //根据参数查询
        List<Customer> customerList = customerService.findByParam(params);
        //一共多少条数据
        Long count = customerService.count();
        //符合条件的有那些
        Long filterCount = customerService.countByParam(params);
        return new DataTablesResult<>(draw, customerList, count, filterCount);
    }

    /**
     * 显示公司详情
     *
     * @return
     */
    @RequestMapping("/company.json")
    @ResponseBody
    public List<Customer> findAllCompany() {
        List<Customer> list = customerService.findAllCompany();
        return list;
    }

    /**
     * 添加用户
     * @param customer
     * @return
     */
    @RequestMapping(value = "/new", method = RequestMethod.POST)
    @ResponseBody
    public AjaxResult addUser(Customer customer) {

        customerService.saveCustomer(customer);

        return new AjaxResult(AjaxResult.SUCCESS);
    }

    /**
     * 删除用户
     * @param id
     * @return
     */
    @RequestMapping(value = "/del/{id:\\d+}", method = RequestMethod.GET)
    @ResponseBody
    public AjaxResult del(@PathVariable Integer id) {
        customerService.delCustomer(id);
        return new AjaxResult(AjaxResult.SUCCESS);
    }
}
