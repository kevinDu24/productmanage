package cn.net.leadu.service;

import cn.net.leadu.config.AppVersionManageProperties;
import cn.net.leadu.dto.message.Message;
import cn.net.leadu.dto.message.MessageType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/**
 * Created by pengchao on 2017/5/25.
 */
@Service
public class SystemService {
    @Autowired
    private AppVersionManageProperties appVersionManageProperties;

    public ResponseEntity<Message> userLogin(String username, String password){
        if(username.equals(appVersionManageProperties.getUsername()) && password.equals(appVersionManageProperties.getPassword())){
            return new ResponseEntity<Message>(new Message(MessageType.MSG_TYPE_SUCCESS), HttpStatus.OK);
        }
        return new ResponseEntity<Message>(new Message(MessageType.MSG_TYPE_ERROR, "用户名或密码错误！"), HttpStatus.OK);
    }
}
