package cn.net.leadu.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Created by pengchao on 2017/5/25.
 */
@Data
@Entity
public class AppInfo {

    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String appFlag; // 0：太盟宝，1：亚驰宝，２：寻车宝
    private String customer;
}
