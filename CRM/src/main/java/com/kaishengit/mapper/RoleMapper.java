package com.kaishengit.mapper;

import com.kaishengit.pojo.Role;

import java.util.List;

/**
 * Created by lenovo on 2017/3/15.
 */
public interface RoleMapper {

    List<Role> findAll();

    Role findById(Integer roleId);
}
