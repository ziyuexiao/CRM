package com.kaishengit.controller;

import com.kaishengit.dto.DataTablesResult;
import com.kaishengit.dto.JsonResult;
import com.kaishengit.exception.NotFoundException;
import com.kaishengit.pojo.User;
import com.kaishengit.pojo.UserLog;
import com.kaishengit.service.UserService;
import com.kaishengit.util.ShiroUtil;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by lenovo on 2017/3/18.
 */
@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    /**
     * 修改密码
     */
    @RequestMapping(value = "/password", method = RequestMethod.GET)
    public String editPassword(){
        return "setting/password";
    }
    @RequestMapping(value = "/password", method = RequestMethod.POST)
    @ResponseBody
    public String editPassword(String password) {
        userService.changePassword(password);
        return "success";
    }
    /**
     * 验证原始密码是否正确,
     * "X-Requested-With"请求头用于判断是否是Ajax请求
     */
    @RequestMapping(value = "/validate/password",method = RequestMethod.GET)
    @ResponseBody
    public String validateOldpassword(@RequestHeader("X-Requested-With") String xRequestedWith, String oldpassword){
        if("XMLHttpRequest".equals(xRequestedWith)) {
            User user = ShiroUtil.getCurrentUser();
            if(user.getPassword().equals(DigestUtils.md5Hex(oldpassword))) {
                return "true";
            }
            return "false";
        } else {
            throw new NotFoundException();
        }
    }
    /**
     * 登录日志
     */
    @RequestMapping(value = "/log", method = RequestMethod.GET)
    public String showUserLog() {
        return "setting/loglist";
    }
    /**
     * 使用datatables将登录日志显示在页面上
     */
    @RequestMapping(value = "/log/load", method = RequestMethod.GET)
    @ResponseBody
    public DataTablesResult<UserLog> userLogs(HttpServletRequest request){
        String draw = request.getParameter("draw");
        String start = request.getParameter("start");
        String length = request.getParameter("length");

        List<UserLog> userLogList = userService.findCurrentUserLog(start,length);
        Long count = userService.findCurrentUserLogCount();

        return new DataTablesResult<>(draw,userLogList,count,count);
    }

}
