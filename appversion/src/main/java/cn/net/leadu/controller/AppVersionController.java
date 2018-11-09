package cn.net.leadu.controller;

import cn.net.leadu.domain.AppVersion;
import cn.net.leadu.dto.message.Message;
import cn.net.leadu.service.AppVersionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Created by LEO on 16/9/22.
 */
@RestController
@RequestMapping("/appVersions")
public class AppVersionController {

    @Autowired
    private AppVersionService appVersionService;


    /**
     * 获取最新版本
     * @param type
     * @return
     */
    @RequestMapping(value = "/latest", method = RequestMethod.GET)
    public ResponseEntity<Message> getLatestAppVersion(Integer type, String appFlag,  @RequestParam(required = false) String version){
        return appVersionService.getLatestAppVersion(type, version, appFlag);
    }

    /**
     * 获取最新版本描述
     * @param type
     * @return
     */
    @RequestMapping(value = "/latest/description", method = RequestMethod.GET, produces = MediaType.TEXT_HTML_VALUE)
    public String getDescription(Integer type, String appFlag){
        return appVersionService.getDescription(appFlag, type);
    }

    /**
     * 发布客户端新版本
     * @param appVersion
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Message> publishNewVersion(@RequestBody AppVersion appVersion){
        return appVersionService.publishNewVersion(appVersion);
    }

    @RequestMapping(value = "/getAppInfo", method = RequestMethod.GET)
    public ResponseEntity<Message> getAppInfo(){
        return appVersionService.getAppInfo();
    }

}
