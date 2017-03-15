package com.kaishengit.service;

import com.kaishengit.mapper.RoleMapper;
import com.kaishengit.mapper.UserMapper;
import com.kaishengit.pojo.Role;
import com.kaishengit.pojo.User;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by lenovo on 2017/3/15.
 */
@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RoleMapper roleMapper;
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
}
