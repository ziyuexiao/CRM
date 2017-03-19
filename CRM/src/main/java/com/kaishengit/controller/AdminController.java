package com.kaishengit.controller;


import com.google.common.collect.Maps;
import com.kaishengit.dto.DataTablesResult;
import com.kaishengit.dto.JsonResult;
import com.kaishengit.pojo.Role;
import com.kaishengit.pojo.User;
import com.kaishengit.service.UserService;
import com.kaishengit.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

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
    /**
     * 用dataTable将所有用户展示在列表中,并进行分页
     */
    @RequestMapping(value = "/users/load",method = RequestMethod.GET)
    @ResponseBody
    public DataTablesResult<User> userList(HttpServletRequest request){
        String draw = request.getParameter("draw");
        String start = request.getParameter("start");
        String length = request.getParameter("length");
        String keyword = request.getParameter("search[value]");
        keyword = Strings.toUTF8(keyword);

        Map<String,Object> searchParam = Maps.newHashMap();
        searchParam.put("start",start);
        searchParam.put("length",length);
        searchParam.put("keyword",keyword);

        List<User> userList = userService.findUserListByParam(searchParam);
        Long count = userService.findtUserCount();
        Long filterCount = userService.findUserCountByParam(searchParam);

        DataTablesResult dataTablesResult = new DataTablesResult<>(draw, userList, count, filterCount);

        return dataTablesResult;
    }
    /**
     * 重置用户密码为000000
     * @return
     */
    @RequestMapping(value = "/users/resetpassword",method = RequestMethod.POST)
    @ResponseBody
    public String resetPassword(Integer id) {
        userService.resetUserPassword(id);
        return "success";
    }

    /**
     * 根据用户的ID显示用户JSON
     * @return
     */
    @RequestMapping(value = "/users/{id:\\d+}.json",method = RequestMethod.GET)
    @ResponseBody
    public JsonResult showUser(@PathVariable Integer id) {
        User user = userService.findUserById(id);
        if(user == null) {
            return new JsonResult("找不到"+id+"对应的用户");
        } else {
            return new JsonResult(user);
        }
    }
    /**
     * 编辑用户
     * @param user
     * @return
     */
    @RequestMapping(value = "/users/edit",method = RequestMethod.POST)
    @ResponseBody
    public String editUser(User user) {
        userService.editUser(user);
        return "success";
    }
}
