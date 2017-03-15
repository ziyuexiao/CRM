package com.kaishengit.mapper;

import com.kaishengit.pojo.User;

/**
 * Created by lenovo on 2017/3/15.
 */
public interface UserMapper {
    void save(User user);

    User findByUsername(String username);
}
