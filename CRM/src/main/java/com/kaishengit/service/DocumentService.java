package com.kaishengit.service;

import com.kaishengit.mapper.DocumentMapper;
import com.kaishengit.pojo.Document;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * Created by wgs on 2017/3/18.
 */
public interface DocumentService {

    List<Document> findByFid(Integer fid);

    void saveNewFolder(Document document);

    void saveNewFile(Integer id, MultipartFile multipartFile);

    void delById(Integer id);

    Document findDocumentById(Integer id);
}
