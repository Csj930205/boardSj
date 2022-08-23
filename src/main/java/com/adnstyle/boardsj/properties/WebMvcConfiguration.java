package com.adnstyle.boardsj.properties;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {

    @Bean
    public CommonsMultipartResolver multipartResolver(){
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
//        파일 인코딩 설정
        multipartResolver.setDefaultEncoding("UTF-8");
//        파일당 업로드 크기 제한(5MB)
        multipartResolver.setMaxUploadSizePerFile(10 * 1024 * 1024);
        return multipartResolver;
    }
}
