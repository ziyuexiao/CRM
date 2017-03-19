package com.kaishengit.service;

import com.google.common.collect.Maps;
import com.kaishengit.mapper.RoleMapper;
import com.kaishengit.mapper.UserLogMapper;
import com.kaishengit.mapper.UserMapper;
import com.kaishengit.pojo.Role;
import com.kaishengit.pojo.User;
import com.kaishengit.pojo.UserLog;
import com.kaishengit.util.ShiroUtil;
import org.apache.commons.codec.digest.DigestUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * Created by lenovo on 2017/3/15.
 */
@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private UserLogMapper userLogMapper;

    private UserService userService;
    /**
     * 添加新用户
     * @param user
     */
    @Transactional
    public void saveUser(User user) {
        user.setEnable(true);
        user.setPassword(DigestUtils.md5Hex(user.getPassword()));

        //TODO 向微信公众平台注册账号

        userMapper.save(user);
    }

    /**
     * 根据用户名查找用户
     * @param username
     * @return
     */
    public User findUserByUserName(String username) {
        return userMapper.findByUsername(username);
    }
    /**
     * 获取所有的角色
     * @return
     */
    public List<Role> findAllRole() {
        return  roleMapper.findAll();
    }
    /**
     * 获取所有的用户
     */
    public List<User> findAllUser() {
        return  userMapper.findAll();
    }

    /**
     * 获取用户数量
     * @return
     */
    public Long findtUserCount() {
        return userMapper.count();
    }

    /**
     * 根据条件获取用户
     * @param searchParam
     * @return
     */
    public List<User> findUserListByParam(Map<String, Object> searchParam) {
        return userMapper.findUserByParam(searchParam);
    }

    /**
     * 获取满足搜寻条件的用户数
     * @param searchParam
     * @return
     */
    public Long findUserCountByParam(Map<String, Object> searchParam) {
        return userMapper.countByParam(searchParam);
    }

    /**
     * 根据id找用户
     * @param id
     * @return
     */
    public User findUserById(Integer id) {
        return userMapper.findById(id);
    }

    /**
     *重置用户密码
     * @param id
     */
    public void resetUserPassword(Integer id) {
        User user = userMapper.findById(id);
        if(user != null) {
            user.setPassword(DigestUtils.md5Hex("000000"));
            userMapper.updateUser(user);
        }
    }

    /**
     * 编辑用户
     * @param user
     */
    public void editUser(User user) {
        userMapper.updateUser(user);
    }

    /**
     * 修改用户密码
     * @param password
     */
    public void changePassword(String password) {
        User user = ShiroUtil.getCurrentUser();
        user.setPassword(DigestUtils.md5Hex(password));

        userMapper.updateUser(user);
    }

    /**
     * 获取用户的登录日志
     * @param start
     * @param length
     * @return
     */

    public List<UserLog> findCurrentUserLog(String start, String length) {
        Map<String,Object> param = Maps.newHashMap();
        param.put("userId",ShiroUtil.getCurrentUserID());
        param.put("start",start);
        param.put("length",length);
        return userLogMapper.findByParam(param);
    }

    /**
     * 获取用户的登录日志的数量
     * @return
     */
    public Long findCurrentUserLogCount() {
        Map<String,Object> param = Maps.newHashMap();
        param.put("userId",ShiroUtil.getCurrentUserID());
        return userLogMapper.countByParam(param);
    }

    /**
     * 保存登录日志
     * @param ip
     */
    public void saveUserLogin(String ip) {
        UserLog userLog = new UserLog();
        userLog.setLogintime(DateTime.now().toString("yyyy-MM-dd HH:mm"));
        userLog.setLoginip(ip);
        userLog.setUserid(ShiroUtil.getCurrentUserID());
        userLogMapper.save(userLog);
    }
}
