package cn.net.leadu.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by LEO on 16/9/20.
 */
@Data
@Entity
public class AppVersion {

    @Id
    @GeneratedValue
    private Long id;
    private String version;
    private String downloadUrl;
    private String description;
    private String oldDesc;
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date updateTime;
    private Integer type; // 类型 0为安卓,1为ios
    private String mustUpdate;
    private String appName;
    private String appFlag;// 0：太盟宝，1：亚驰宝, ２：寻车宝
}
