package cn.net.leadu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.feign.EnableFeignClients;

/**
 * Created by pengchao on 2017/5/17.
 */
@SpringBootApplication
@EnableFeignClients
public class AppVersionApplication {
    public static void main(String[] args) {
        SpringApplication.run(AppVersionApplication.class, args);
    }
}
