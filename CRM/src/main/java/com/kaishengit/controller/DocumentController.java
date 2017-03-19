package com.kaishengit.controller;

import com.kaishengit.dto.AjaxResult;
import com.kaishengit.exception.ServiceException;
import com.kaishengit.pojo.Document;
import com.kaishengit.service.DocumentService;
import org.apache.ibatis.javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.rmi.server.ServerCloneException;
import java.util.List;

@Controller
@RequestMapping("/doc")
public class DocumentController {
    @Value("${path.file}")
    private String savePath;
    //注入DocumentService
    @Autowired
    private DocumentService documentService;

    /**
     * 显示网盘列表
     *
     * @param fid   0表示根目录 1表示子目录
     * @param model
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public String list(@RequestParam(required = false, defaultValue = "0") Integer fid, Model model) {

        //数据库查询当前目录下内容

        List<Document> documentList = documentService.findByFid(fid);

        //设置到页面
        model.addAttribute("documentList", documentList);
        model.addAttribute("fid", fid);

        return "document/list";
    }

    /**
     * 创建文件夹
     *
     * @param
     * @return
     */
    @RequestMapping(value = "/dir/new", method = RequestMethod.POST)
    @ResponseBody
    public AjaxResult saveFolder(Document document) {
        System.out.println(document);
        documentService.saveNewFolder(document);
        return new AjaxResult(AjaxResult.SUCCESS);
    }

    /**
     * 上传文件
     *
     * @param file
     * @param fid
     * @return
     * @throws IOException
     */

    @RequestMapping(value = "/file/upload", method = RequestMethod.POST)
    @ResponseBody
    public AjaxResult saveFile(MultipartFile file, Integer fid) throws IOException {

        if (file.isEmpty()) {
            throw new ServiceException();
        } else {
            documentService.saveNewFile(fid, file);

        }
        return new AjaxResult(AjaxResult.SUCCESS);
    }

    /**
     * 删除文件
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/del/{id:\\d+}", method = RequestMethod.GET)
    @ResponseBody
    public AjaxResult del(@PathVariable Integer id) {
        //根据id删除
        documentService.delById(id);
        return new AjaxResult(AjaxResult.SUCCESS);

    }

    @RequestMapping(value = "/download/{id:\\d+}", method = RequestMethod.GET)
    public ResponseEntity<InputStreamResource> downloadFile(@PathVariable Integer id) throws FileNotFoundException, UnsupportedEncodingException {
        Document document = documentService.findDocumentById(id);
        if (document == null) {
            throw new com.kaishengit.exception.NotFoundException();
        }
        //封装文件路径
        File file = new File(savePath, document.getFilename());
        if (!file.exists()) {
            throw new com.kaishengit.exception.NotFoundException();

        }
        FileInputStream fileInputStream = new FileInputStream(file);
        //获取数据库对应的文件名字
        String fileName = document.getName();
        fileName = new String(fileName.getBytes("UTF-8"), "ISO8859-1");

        return ResponseEntity
                .ok()
                //设置二进制minitype
                .contentType(MediaType.parseMediaType(document.getContexttype()))
                .contentLength(file.length())
                //设置下载的文件名
                .header("Content-Disposition", "attachment;filename=\"" + fileName + "\"")
                .body(new InputStreamResource(fileInputStream));
    }
}
