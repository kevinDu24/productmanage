package cn.net.leadu.controller;

import cn.net.leadu.dto.message.Message;
import cn.net.leadu.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by pengchao on 2017/5/25.
 */
@RestController
@RequestMapping("/system")
public class SystemController {

    @Autowired
    private SystemService systemService;

    /**
     * 版本发布用户验证
     * @param username
     * @param password
     * @return
     */
    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public ResponseEntity<Message> userLogin(String username, String password){
        return systemService.userLogin(username, password);
    }

}
