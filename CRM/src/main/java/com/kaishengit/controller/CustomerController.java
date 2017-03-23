package com.kaishengit.controller;

import com.google.common.collect.Maps;
import com.kaishengit.dto.AjaxResult;
import com.kaishengit.dto.DataTablesResult;
import com.kaishengit.exception.ForbiddenException;
import com.kaishengit.exception.NotFoundException;
import com.kaishengit.pojo.Customer;
import com.kaishengit.pojo.Sales;
import com.kaishengit.pojo.User;
import com.kaishengit.service.CustomerService;
import com.kaishengit.service.SalesService;
import com.kaishengit.service.UserService;
import com.kaishengit.util.ShiroUtil;
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
    private UserService userService;
    @Autowired
    private SalesService salesService;
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
     *
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
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/del/{id:\\d+}", method = RequestMethod.GET)
    @ResponseBody
    public AjaxResult del(@PathVariable Integer id) {
        customerService.delCustomer(id);
        return new AjaxResult(AjaxResult.SUCCESS);
    }

    /**
     * 编辑客户
     *
     * @param id
     * @return
     */
    @RequestMapping("/edit/{id:\\d+}.json")
    @ResponseBody
    public Map<String, Object> editCustomer(@PathVariable Integer id) {
        Map<String, Object> result = Maps.newHashMap();

        //1.根据对应的id查找客户
        Customer customer = customerService.findCustomerById(id);
        //2.判断是否为空
        if (customer == null) {
            result.put("state", "error");
            result.put("message", "找不到对应的客户");
        } else {
            List<Customer> companyList = customerService.findAllCompany();
            result.put("state", "success");
            result.put("customer", customer);
            result.put("companyList", companyList);
        }
        return result;
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    @ResponseBody
    public String edit(Customer customer) {
        customerService.editCustomer(customer);
        return "success";
    }

    /**
     * 显示客户信息
     *
     * @return
     */
    @RequestMapping(value = "{id:\\d+}", method = RequestMethod.GET)
    public String showCustomer(@PathVariable Integer id, Model model) {
        //根据id查找用户
        Customer customer = customerService.findCustomerById(id);
        //判断用户是否存在
        if (customer == null) {
            throw new NotFoundException();
        }
        //公共的用户，不是我的用户，不是经理，不让看
        if (customer.getUserid() != null && !customer.getUserid().equals(ShiroUtil.getCurrentUserID()) && !ShiroUtil.isManager()) {
            throw new ForbiddenException();
        }

        model.addAttribute("customer", customer);

        //查找公司所用的客户
        if (customer.getType().equals(Customer.CUSTOMER_TYPE_COMPANY)) {
            List<Customer> customerList = customerService.findCompanyCustomerById(id);
            model.addAttribute("customerList", customerList);
        }

        //加载所有员工

        List<User> userList = userService.findAllUser();
        model.addAttribute("userList", userList);

        //加载客户对应的销售机会列表
      /*  List<Sales> salesList = salesService.findSalesByCustId(id);
        model.addAttribute("salesList", salesList);*/


        return "customer/view";
    }

    /**
     * 公开用户
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/open/{id:\\d+}", method = RequestMethod.GET)
    public String openCustomer(@PathVariable Integer id) {

        //根据id获取用户
        Customer customer = customerService.findCustomerById(id);

        if (customer == null) {
            throw new NotFoundException();
        }
        if (customer.getUserid() != null && !customer.getUserid().equals(ShiroUtil.getCurrentUserID()) && !ShiroUtil.isManager()) {
            throw new ForbiddenException();
        }
        customerService.openCustomer(customer);
        //重定向修改的id
        return "redirect:/customer/" + id;
    }

    /**
     * 转移客户
     */
    @RequestMapping(value = "/move", method = RequestMethod.POST)
    public String moveCust(Integer id, Integer userid) {
        Customer customer = customerService.findCustomerById(id);
        if (customer == null) {
            throw new NotFoundException();
        }
        if (customer.getUserid() != null && !customer.getUserid().equals(ShiroUtil.getCurrentUserID()) && !ShiroUtil.isManager()) {
            throw new ForbiddenException();
        }
        customerService.moveCust(customer, userid);
        //回到列表页
        return "redirect:/customer";
    }
}
