package com.kaishengit.controller;

import com.google.common.collect.Maps;
import com.kaishengit.dto.DataTablesResult;
import com.kaishengit.exception.NotFoundException;
import com.kaishengit.pojo.Notice;
import com.kaishengit.pojo.User;
import com.kaishengit.service.NoticeService;
import com.kaishengit.util.ShiroUtil;
import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;
import org.joda.time.DateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by lenovo on 2017/3/18.
 */
@Controller
@RequestMapping("/notice")
public class NoticeController {
    @Value("${qiniu.ak}")
    private String ak;
    @Value("${qiniu.sk}")
    private String sk;
    @Value("${qiniu.domain}")
    private String domain;
    @Value("${qiniu.storage}")
    private String storage;
    @Autowired
    private NoticeService noticeService;
    /**
     * 显示公告
     */
    @RequestMapping
    public String noticeList(){
       return "notice/list";
    }
    @RequestMapping("/load")
    @ResponseBody
    public DataTablesResult loadNotice(HttpServletRequest request){
        String draw = request.getParameter("draw");
        String start = request.getParameter("start");
        String length = request.getParameter("length");

        Map<String,Object> param = Maps.newHashMap();
        param.put("draw",draw);
        param.put("start",start);
        param.put("length",length);

        List<Notice> noticeList = noticeService.findByParam(param);
        Long count = noticeService.count();
        return new DataTablesResult(draw,noticeList,count,count);
    }
    /**
     * 发表公告
     */
    @RequestMapping(value = "/new",method = RequestMethod.GET)
    public String newNotice(HttpServletRequest request, Model model){
        if (ShiroUtil.isManager()){
            //获取七牛上传的token
            Auth auth = Auth.create(ak,sk);
            StringMap map = new StringMap();
            String returnBody = "{ \"success\": true,\"file_path\": \""+domain+"${key}\"}";
            map.put("returnBody",returnBody);

            String token = auth.uploadToken(storage,null,3600,map);

            model.addAttribute("token",token);
            return "notice/new";
        }else {
            return "";
        }
    }
    @RequestMapping(value = "/new",method = RequestMethod.POST)
    public String newNotice(Notice notice,RedirectAttributes redirectAttributes){
        noticeService.saveNotice(notice);
        redirectAttributes.addFlashAttribute("message","发表成功");
        return "redirect:/notice";
    }
    /**
     * 根据公告id显示具体内容
     */
    @RequestMapping(value = "/{id:\\d+}",method = RequestMethod.GET)
    public String viewNotice(@PathVariable Integer id, Model model) {
        Notice notice = noticeService.findNoticeById(id);
        User user = ShiroUtil.getCurrentUser();
        Date date=new Date();
        DateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time=format.format(date);
        if(notice == null) {
            throw new NotFoundException();
        }
        model.addAttribute("notice",notice);
        model.addAttribute("user",user);
        model.addAttribute("time",time);
        return "notice/view";
    }

}
