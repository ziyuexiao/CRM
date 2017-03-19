package com.kaishengit.mapper;

import com.kaishengit.pojo.UserLog;

import java.util.List;
import java.util.Map;

/**
 * Created by lenovo on 2017/3/18.
 */
public interface UserLogMapper {
    List<UserLog> findByParam(Map<String, Object> param);

    void save(UserLog userLog);

    Long countByParam(Map<String, Object> param);
}
