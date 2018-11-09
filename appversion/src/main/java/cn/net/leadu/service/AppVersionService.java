package cn.net.leadu.service;

import cn.net.leadu.dao.AppInfoRepository;
import cn.net.leadu.dao.AppVersionRepository;
import cn.net.leadu.domain.AppInfo;
import cn.net.leadu.domain.AppVersion;
import cn.net.leadu.dto.appVersion.AppVersionDto;
import cn.net.leadu.dto.message.Message;
import cn.net.leadu.dto.message.MessageType;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

/**
 * Created by LEO on 16/11/30.
 */
@Service
public class AppVersionService {

    @Autowired
    private AppVersionRepository appVersionRepository;

    @Autowired
    private AppInfoRepository appInfoRepository;
    @Autowired
    private HttpServletRequest httpServletRequest;

    public ResponseEntity<Message> getLatestAppVersion(Integer type, String version, String appFlag){
        List<AppVersion> appVersions = appVersionRepository.findByTypeAndAppFlagOrderByUpdateTimeDesc(type, appFlag);
        AppVersion latestVersion = appVersions.size() == 0 ? null : appVersions.get(0);
        if(latestVersion != null && latestVersion.getType().equals(0)){
            if(version == null){
                String urls[] = latestVersion.getDownloadUrl().split("http://222.73.56.27:5087/android/");
                if(urls.length == 1){
                    latestVersion.setDownloadUrl(urls[0]);
                }else if(urls.length > 1){
                    latestVersion.setDownloadUrl(urls[1]);
                }

            }
        }
        AppVersionDto appVersionDto = new AppVersionDto();
        BeanUtils.copyProperties(latestVersion, appVersionDto);
        return new ResponseEntity<Message>(new Message(MessageType.MSG_TYPE_SUCCESS, appVersionDto), HttpStatus.OK);
    }

    public String getDescription(String appFlag, Integer type){
        List<AppVersion> appVersions = appVersionRepository.findByTypeAndAppFlagOrderByUpdateTimeDesc(type, appFlag);
        return appVersions.size() == 0 ? "" : appVersions.get(0).getDescription();
    }

    public ResponseEntity<Message> publishNewVersion(@RequestBody AppVersion appVersion){
        String description = "<html lang=\"en\" data-ng-app=\"app\"><head><meta charset=\"UTF-8\">\n" +
                "    <style type=\"text/css\" rel=\"stylesheet\">\n" +
                "        body { -webkit-overflow-scrolling: touch;}\n" +
                "        .newsWrapper{padding: 10px;line-height: 28px; background: #fff;}\n" +
                "        .newsWrapper p{font-size: 16px;}\n" +
                "        .newsWrapper img{text-align: center}\n" +
                "        .newsWrapper h1,.newsWrapper h2,.newsWrapper h3,.newsWrapper h4,.newsWrapper h5,.newsWrapper h6{line-height: 24px;}\n" +
                "        .newsWrapper hr{border: 1px dashed #ccc;}\n" +
                "    </style>\n" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1, maximum-scale=1\" />\n" +
                "    <link rel=\"stylesheet\" href=\"http://wx.xftm.com/css/app.min.css\" type=\"text/css\" />\n" +
                "</head>\n" +
                "<body>\n" +
                "<div class=\"newsWrapper\">" +
                appVersion.getDescription() + "</div>\n" +
                "</body>\n" +
                "</html>";
        appVersion.setDescription(description);
        appVersion.setUpdateTime(new Date());
        appVersionRepository.save(appVersion);
        return new ResponseEntity<Message>(new Message(MessageType.MSG_TYPE_SUCCESS), HttpStatus.OK);
    }


    public ResponseEntity<Message> getAppInfo(){
        List<AppInfo> appVersions = appInfoRepository.findAll();
        if(appVersions.size() != 0){
            return new ResponseEntity<Message>(new Message(MessageType.MSG_TYPE_SUCCESS, appVersions), HttpStatus.OK);
        }
        return new ResponseEntity<Message>(new Message(MessageType.MSG_TYPE_ERROR, "未查询到结果"), HttpStatus.OK);
    }


}
