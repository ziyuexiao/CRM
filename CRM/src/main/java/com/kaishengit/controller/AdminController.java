package com.kaishengit.controller;

import com.kaishengit.pojo.Role;
import com.kaishengit.pojo.User;
import com.kaishengit.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by lenovo on 2017/3/15.
 */
@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private UserService userService;
    @RequestMapping(value = "/users",method = RequestMethod.GET)
    public String userList(Model model) {

        List<Role> roleList = userService.findAllRole();

        model.addAttribute("roleList",roleList);

        return "admin/userlist";
    }
    /**
     * 添加新用户
     * @return
     */
    @RequestMapping(value = "/users/new",method = RequestMethod.POST)
    @ResponseBody
    public String saveUser(User user) {
        userService.saveUser(user);
        return "success";
    }
    /**
     * 验证用户名是否可用（Ajax调用）
     * @param username
     * @return
     */
    @RequestMapping(value = "/user/checkusername",method = RequestMethod.GET)
    @ResponseBody
    public String checkUserName(String username) {
        User user = userService.findUserByUserName(username);
        if(user == null) {
            return "true";
        }
        return "false";
    }
}
