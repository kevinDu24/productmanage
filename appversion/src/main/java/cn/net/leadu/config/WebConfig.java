package cn.net.leadu.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.aop.interceptor.SimpleAsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.concurrent.Executor;

/**
 * Created by pengchao on 2016/9/10.
 */
@Configuration
@EnableScheduling
public class WebConfig extends WebMvcConfigurerAdapter implements AsyncConfigurer {
    @Bean
    public ObjectMapper objectMapper(){
        return new ObjectMapper();
    }

    @Bean
    RestTemplate restTemplate(){

        HttpComponentsClientHttpRequestFactory httpRequestFactory = new HttpComponentsClientHttpRequestFactory();
        httpRequestFactory.setReadTimeout(20 * 1000);

        return new RestTemplate(httpRequestFactory);
    }

    @Bean
    public FileUploadProperties fileUploadProperties(){
        return new FileUploadProperties();
    }

    @Bean
    public AppVersionManageProperties appVersionManageProperties(){
        return new AppVersionManageProperties();
    }



//    @Bean
//    public WxMpService wxMpService(){
//        WxMpInMemoryConfigStorage config = new WxMpInMemoryConfigStorage();
//        config.setAppId(WeChatConsts.APPID.value());
//        config.setSecret(WeChatConsts.APPSECRET.value());
//        config.setToken(WeChatConsts.TOKEN.value());
//        WxMpService wxMpService = new WxMpServiceImpl();
//        wxMpService.setWxMpConfigStorage(config);
//        return wxMpService;
//    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
    }

    @Override
    public void configurePathMatch(PathMatchConfigurer configurer){
        configurer.setUseSuffixPatternMatch(false);
    }

    @Override
    public Executor getAsyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(10);
        executor.setMaxPoolSize(2000);
        executor.setQueueCapacity(2000);
        executor.initialize();
        return executor;
    }

    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return new SimpleAsyncUncaughtExceptionHandler();
    }
}
