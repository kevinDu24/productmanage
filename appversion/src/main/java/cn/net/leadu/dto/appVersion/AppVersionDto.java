package cn.net.leadu.dto.appVersion;

import lombok.Data;

import java.util.Date;

/**
 * Created by LEO on 16/9/20.
 */
@Data
public class AppVersionDto {
    private String version;
    private String downloadUrl;
    private String description;
    private Date updateTime;
    private String mustUpdate;
}
