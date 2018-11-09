package cn.net.leadu.controller;

import cn.net.leadu.dto.message.Message;
import cn.net.leadu.service.FileUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created by LEO on 16/10/9.
 */
@RestController
@RequestMapping("/files")
public class FileController {
    @Autowired
    private FileUploadService fileUploadService;

    /**
     * 文件上传
     * @param type
     * @param file
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Message> uploadFile(String type, MultipartFile file){
        return fileUploadService.uploadFile(type, file);
    }
}
