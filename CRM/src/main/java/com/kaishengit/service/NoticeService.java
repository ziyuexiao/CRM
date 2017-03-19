package com.kaishengit.service;

import com.kaishengit.mapper.NoticeMapper;
import com.kaishengit.pojo.Notice;
import com.kaishengit.util.ShiroUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by lenovo on 2017/3/18.
 */

@Service
public class NoticeService {
    @Autowired
    private NoticeMapper noticeMapper;

    /**
     * 显示公告
     * @param param
     * @return
     */
    public List<Notice> findByParam(Map<String, Object> param) {
        return noticeMapper.findNoticeByParam(param);
    }

    /**
     * 公告数量
     * @return
     */
    public Long count() {
        return noticeMapper.count();
    }

    /**
     * 保存公告
     * @param notice
     */
    public void saveNotice(Notice notice) {
        notice.setUserid(ShiroUtil.getCurrentUserID());
        notice.setRealname(ShiroUtil.getCurrentRealName());
        noticeMapper.save(notice);
        //todo 微信通知成员
    }

    /**
     * 根据公告id查找具体公告
     * @param id
     * @return
     */
    public Notice findNoticeById(Integer id) {
        return noticeMapper.findById(id);
    }
}
