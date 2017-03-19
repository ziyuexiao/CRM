package com.kaishengit.service.imp;

import com.google.common.collect.Lists;
import com.kaishengit.dto.AjaxResult;
import com.kaishengit.exception.ServiceException;
import com.kaishengit.mapper.DocumentMapper;
import com.kaishengit.pojo.Document;
import com.kaishengit.service.DocumentService;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.List;
import java.util.UUID;

@Service
public class DocumentServiceImpl implements DocumentService {
    //注入DocumentMapper
    @Autowired
    private DocumentMapper documentMapper;
    //获取配置文件路径
    @Value("${path.file}")
    private String savePath;


    public List<Document> findByFid(Integer fid) {
        List<Document> documentList = documentMapper.findByFid(fid);
        return documentList;
    }

    /**
     * 新建文件夹
     *
     * @param document
     */
    @Override
    public void saveNewFolder(Document document) {
        //设置操作人时间。。
      //TODO
        //document.setName();
        document.setType(Document.TYPE_DIR);
        document.setFid(document.getFid());

        documentMapper.saveNewFolder(document);
    }

    /**
     * 上传文件
     * @param fid
     * @param file
     */
    @Override
    public void saveNewFile(Integer fid, MultipartFile file) {
        //保存到本地
        //文件大小 类型
        //文件原始名字
        String sourceName = file.getOriginalFilename();
        //保存新名字
        String newName = UUID.randomUUID().toString();
        //大小
        Long size = file.getSize();
        //
        String  contentType = file.getContentType();
        if(sourceName.lastIndexOf(".") != -1){
            newName = newName + sourceName.substring(sourceName.lastIndexOf("."));
        }

        //保存硬盘的路经设置
        File path = new File(savePath);
        if(! path.exists()){
            path.mkdir();
        }
        File saveFile = new File(path,newName);
        try {
            InputStream inputStream = file.getInputStream();
            FileOutputStream outputStream = new FileOutputStream(saveFile);
            IOUtils.copy(inputStream,outputStream);
            inputStream.close();
            outputStream.flush();
            outputStream.close();
        } catch (IOException e) {
            throw new ServiceException("保存到硬盘异常", e);
        }

        //保存到数据库
        Document document = new Document();
        document.setName(sourceName);
        document.setFid(fid);
        document.setType(Document.TYPE_DOC);
       // document.setCreateuser(ShiroUtil.getCurrentRealName());
        document.setContexttype(contentType);
        document.setSize(FileUtils.byteCountToDisplaySize(size));
        //document.setMd5(md5);
        document.setFilename(newName);
        //TODO
        //document.setCreateuser();

       documentMapper.saveNewFolder(document);
    }

    /**
     * 删除文件
     * @param id
     */
    @Override
    public void delById(Integer id) {
        //1.根据查找是否文件
        Document document = documentMapper.findId(id);
        //2.判断是否文件。
        if(document != null){
            if(Document.TYPE_DOC.equals( document.getType())){
                //是文件直接删
                File file = new File(savePath,document.getName());
                file.delete();
                //从数据库中删除
                documentMapper.delById(id);
            }else {
                //如果不是递归删除
                List<Document> documentList = documentMapper.findAll();//查找所有
                List<Integer> delIdList = Lists.newArrayList();//即将要删除的id
                findDelId(documentList,delIdList,id);
                //最后把文件夹id加入
                delIdList.add(id);
                //批量删除
                documentMapper.batchDel(delIdList);
            }
        }

    }

    /**
     * 根据id找对应的文件
     * @param id
     * @return
     */
    @Override
    public Document findDocumentById(Integer id) {
        return documentMapper.findId(id);
    }

    private void findDelId(List<Document> documentList, List<Integer> delIdList, Integer id) {

        for (Document disk : documentList) {
            if (disk.getFid().equals(id)) {
                //把子文件id添加到集合中
                delIdList.add(disk.getId());
                //判断是否为文件
                if (disk.getType().equals(Document.TYPE_DOC)) {
                    File file = new File("savePath", disk.getName());
                    //删除文件
                    file.delete();
                } else {
                    //为文件夹:递归删除
                    findDelId(documentList, delIdList, id);

                }
            }

        }

/*

        for(Document document : documentList){
            //判断是否是它的子文件或者文件夹
            if(document.getFid().equals(id)){
                //是，放id入集合中
                delIdList.add(document.getId());
            }
            if(Document.TYPE_DIR.equals(document.getType())){
                //如果是文件
                File file = new File(savePath,document.getName());
                file.delete();
            }else {
                //最后删除文件夹

                findDelId(documentList,delIdList,id);
            }
        }
*/

    }

    @RequestMapping("/file/upload")
    @ResponseBody
    public AjaxResult saveFile() {
        return new AjaxResult(AjaxResult.SUCCESS);
    }
}
