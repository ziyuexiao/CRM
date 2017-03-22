package com.kaishengit.controller;

import com.google.common.collect.Maps;
import com.kaishengit.dto.DataTablesResult;
import com.kaishengit.pojo.Sales;
import com.kaishengit.service.CustomerService;
import com.kaishengit.service.SalesService;
import com.kaishengit.util.ShiroUtil;
import com.kaishengit.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * Created by lenovo on 2017/3/22.
 */
@Controller
@RequestMapping("/sales")
public class SalesController {
    @Autowired
    private CustomerService customerService;
    @Autowired
    private SalesService salesService;
    /**
     * 展示机会列表
     * @param model
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public String list(Model model){

            /*Integer userid = ShiroUtil.getCurrentUserID();
            model.addAttribute("customerList",customerService.findAllCustomerByUserid(userid));*/
            return "sales/list";


    }
    @RequestMapping(value="/load",method =RequestMethod.GET )
    @ResponseBody
    public DataTablesResult<Sales> load(HttpServletRequest request) {
        String draw = request.getParameter("draw");
        String start = request.getParameter("start");
        String length = request.getParameter("length");
        //query param
        String name = request.getParameter("name");
        name = Strings.toUTF8(name);
        String progress = request.getParameter("progress");
        progress = Strings.toUTF8(progress);
        String startDate = request.getParameter("startdate");
        String endDate = request.getParameter("enddate");

        Map<String,Object> params = Maps.newHashMap();
        params.put("start",start);
        params.put("length",length);
        params.put("name",name);
        params.put("progress",progress);
        params.put("startdate",startDate);
        params.put("enddate",endDate);

        List<Sales> salesList = salesService.findByParam(params);
        Long count = salesService.count();
        Long countParam = salesService.countByParam(params);

        return new DataTablesResult<>(draw,salesList,count,countParam);
    }
    /**
     * 新增机会
     */
    @RequestMapping(value = "/new",method = RequestMethod.POST)
    @ResponseBody
    public String saveSales(Sales sales){
        salesService.saveSales(sales);
        return "success";
    }

}
