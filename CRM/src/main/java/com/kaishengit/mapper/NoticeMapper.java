package com.kaishengit.mapper;

import com.kaishengit.pojo.Notice;

import java.util.List;
import java.util.Map;

/**
 * Created by lenovo on 2017/3/18.
 */
public interface NoticeMapper {
    List<Notice> findNoticeByParam(Map<String, Object> param);

    Long count();

    void save(Notice notice);

    Notice findById(Integer id);
}
