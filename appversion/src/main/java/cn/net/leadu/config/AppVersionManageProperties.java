package cn.net.leadu.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Created by pengchao on 2017/5/25.
 */
@ConfigurationProperties(prefix = "appVersionPublishAuth")
@Data
public class AppVersionManageProperties {
    private String username;
    private String password;
}
