package cn.net.leadu.service;

import cn.net.leadu.config.FileUploadProperties;
import cn.net.leadu.domain.AppVersion;
import cn.net.leadu.dto.message.Message;
import cn.net.leadu.dto.message.MessageType;
import cn.net.leadu.utils.CommonUtils;
import com.google.common.collect.Maps;
import com.jayway.restassured.path.json.JsonPath;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;

/**
 * Created by pengchao on 2016/3/3.
 */
@Service
public class FileUploadService {
    @Autowired
    private FileUploadProperties fileUploadProperties;

    @Autowired
    private FileDownLoadService wechatService;
    @Autowired
    private HttpServletRequest httpServletRequest;


    /**
     * android APK文件上传
     * @param appVersion
     * @param androidAPK
     * @return
     */
    @Async
    public ResponseEntity<Message> androidAPKUpload(AppVersion appVersion, MultipartFile androidAPK){
        String fileName = androidAPK.getOriginalFilename() + "_" + appVersion.getVersion()  + ".apk";
        try {
            FileUtils.writeByteArrayToFile(new File(fileUploadProperties.getAndroidFilePath() + fileName), androidAPK.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<Message>(new Message(MessageType.MSG_TYPE_ERROR), HttpStatus.OK);
        }
        return new ResponseEntity<Message>(new Message(MessageType.MSG_TYPE_SUCCESS), HttpStatus.OK);
    }

    /**
     * 需要导入的excel文件上传至服务器
     * @param multipartFile
     * @return
     */
    public String excelFileUpload(MultipartFile multipartFile){
        String fileName = UUID.randomUUID().toString() + CommonUtils.getFileSuffix(multipartFile.getOriginalFilename());
        try {
            FileUtils.writeByteArrayToFile(new File(fileUploadProperties.getImpExcelPath() + fileName), multipartFile.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileName;

    }

    /**
     * 文件上传转发器
     * @param type
     * @param file
     * @return
     */
    public ResponseEntity<Message> uploadFile(String type, MultipartFile file){
        if(type.equals("newsImg")){
            return saveFile(getFileName(file), fileUploadProperties.getNewsFilePath(), file, fileUploadProperties.getRequestNewsFilePath());
        }else if(type.equals("companyFile")){
            return saveFile(getFileName(file), fileUploadProperties.getCompanyFilePath(), file, fileUploadProperties.getRequestCompanyFilePath());
        }else if(type.equals("apkUpload")){
            String headerParam = httpServletRequest.getHeader("Header-Param");
            String customer = JsonPath.from(headerParam).get("systemflag");
            if(customer.equals("taimeng")){
                return saveFile("TaiMengBao"+ CommonUtils.getFileSuffix(file.getOriginalFilename()), fileUploadProperties.getAndroidFilePath(), file, fileUploadProperties.getRequestAndroidFilePath());
            } else if(customer.equals("yachi")){
                return saveFile("YaChiBao"+ CommonUtils.getFileSuffix(file.getOriginalFilename()), fileUploadProperties.getAndroidFilePath(), file, fileUploadProperties.getRequestAndroidFilePath());
            } else if(customer.equals("leaduxunche")){
                return saveFile("XunCheBao"+ CommonUtils.getFileSuffix(file.getOriginalFilename()), fileUploadProperties.getAndroidFilePath(), file, fileUploadProperties.getRequestAndroidFilePath());
            } else if(customer.equals("xinyong")){
                return saveFile("XinYongBao"+ CommonUtils.getFileSuffix(file.getOriginalFilename()), fileUploadProperties.getAndroidFilePath(), file, fileUploadProperties.getRequestAndroidFilePath());
            } else if(customer.equals("shangjin")){
                return saveFile("ShangJinXunChe"+ CommonUtils.getFileSuffix(file.getOriginalFilename()), fileUploadProperties.getAndroidFilePath(), file, fileUploadProperties.getRequestAndroidFilePath());
            } else if(customer.equals("guanjia")){
                return saveFile("XunCheGuanJia"+ CommonUtils.getFileSuffix(file.getOriginalFilename()), fileUploadProperties.getAndroidFilePath(), file, fileUploadProperties.getRequestAndroidFilePath());
            }
        }
        return new ResponseEntity<Message>(new Message(MessageType.MSG_TYPE_ERROR, "未指定上传类型"), HttpStatus.OK);
    }

    /**
     * 保存文件
     * @param savePath
     * @param file
     * @param serverPath
     * @return
     */
    private ResponseEntity<Message> saveFile(String fileName, String savePath, MultipartFile file, String serverPath){
        Message message = null;
        if (!file.isEmpty()) {
            try {
//              System.out.println(file.getBytes());
                FileUtils.writeByteArrayToFile(new File(savePath + fileName), file.getBytes());
                Map map = Maps.newHashMap();
                map.put("url", serverPath + fileName);
//              System.out.println(savePath + fileName);
                return new ResponseEntity<Message>(new Message(MessageType.MSG_TYPE_SUCCESS, map), HttpStatus.OK);
            } catch (Exception e) {
                e.printStackTrace();
                return new ResponseEntity<Message>(new Message(MessageType.MSG_TYPE_ERROR, "文件上传失败"), HttpStatus.OK);
            }
        }
        return new ResponseEntity<Message>(new Message(MessageType.MSG_TYPE_ERROR, "文件为空,上传失败"), HttpStatus.OK);
    }

    private String getFileName(MultipartFile file){
        return UUID.randomUUID().toString() + CommonUtils.getFileSuffix(file.getOriginalFilename());
    }
}
