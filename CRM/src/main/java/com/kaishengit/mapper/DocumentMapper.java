package com.kaishengit.mapper;

import com.kaishengit.pojo.Document;

import java.util.List;

/**
 * Created by wgs on 2017/3/18.
 */
public interface DocumentMapper {
    List<Document> findByFid(Integer fid);

    void saveNewFolder(Document document);

    Document findId(Integer id);

    void delById(Integer id);

    List<Document> findAll();

    void batchDel(List<Integer> delIdList);
}
