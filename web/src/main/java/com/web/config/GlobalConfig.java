package com.web.config;

import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;

@Configuration
public class GlobalConfig {
    @Bean
    public WebServerFactoryCustomizer<TomcatServletWebServerFactory> customizer() {
        return (factory) -> {
            factory.addContextCustomizers((context) -> {
                        //ģ����webapp���·��
                        String relativePath = "web/src/main/webapp";
                        File docBaseFile = new File(relativePath);
                        if (docBaseFile.exists()) {
                            context.setDocBase(docBaseFile.getAbsolutePath());
                        }
                    }
            );
        };
    }
}
