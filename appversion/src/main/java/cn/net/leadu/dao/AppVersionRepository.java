package cn.net.leadu.dao;


import cn.net.leadu.domain.AppVersion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by LEO on 16/9/20.
 */
public interface AppVersionRepository extends JpaRepository<AppVersion, Long> {
    List<AppVersion> findByTypeAndAppFlagOrderByUpdateTimeDesc(Integer type, String appFlag);
}
