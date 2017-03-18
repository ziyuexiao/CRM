package com.kaishengit.mapper;

import com.kaishengit.pojo.User;

import java.util.List;
import java.util.Map;

/**
 * Created by lenovo on 2017/3/15.
 */
public interface UserMapper {
    void save(User user);

    User findByUsername(String username);

    List<User> findAll();

    Long count();

    List<User> findUserByParam(Map<String, Object> searchParam);

    Long countByParam(Map<String, Object> searchParam);

    User findById(Integer id);

    void updateUser(User user);
}
