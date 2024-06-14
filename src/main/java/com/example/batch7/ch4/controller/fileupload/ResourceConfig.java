package com.example.batch7.ch4.controller.fileupload;


import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//step 1
@Configuration
public class ResourceConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**").allowedMethods()
                .allowedMethods("HEAD", "GET", "PUT", "POST", "DELETE", "PATCH");
    }

    @Override // cara call engpoint : localhost:9090/api/showFile/namafile.png:
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        registry.addResourceHandler("/pp/**").addResourceLocations("file:cdn/pp/");
        registry.addResourceHandler("/showFile/**").addResourceLocations("file:cdn/");
//        http://localhost:8083/api/v1/showFile/5bf3064e-d31d-46b2-a88a-1336e89af424.pdf
//        http://localhost:8083/api/showFile/5bf3064e-d31d-46b2-a88a-1336e89af424.pdf

        /*
        1.Otomatis Download karena ada V1: http://localhost:8081/api/v1/showFile/a3f42c21-662f-4143-90d5-8f1e98db466e.png
        2. Show only : http://localhost:8081/api/showFile/a3f42c21-662f-4143-90d5-8f1e98db466e.png
         */
    }
}
