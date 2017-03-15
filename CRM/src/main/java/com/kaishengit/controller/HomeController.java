package com.kaishengit.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by lenovo on 2017/3/15.
 */
@Controller
public class HomeController {
    /**
     * 去登录页面
     * @return
     */
    @RequestMapping(value = "/",method = RequestMethod.GET)
    public String index() {
        return "login";
    }

}
