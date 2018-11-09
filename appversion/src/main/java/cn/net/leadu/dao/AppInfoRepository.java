package cn.net.leadu.dao;

import cn.net.leadu.domain.AppInfo;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by pengchao on 2017/5/25.
 */
public interface AppInfoRepository extends JpaRepository<AppInfo, Long> {
}
