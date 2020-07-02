package com.web.config.threadScope;

import org.springframework.beans.factory.config.CustomScopeConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;

@Configuration
public class ScopeConfigurer {
    @Bean
    public static CustomScopeConfigurer customScopeConfigurer() {
        final CustomScopeConfigurer configurer = new CustomScopeConfigurer();
        configurer.setScopes(Collections.singletonMap(ThreadScope.SCOPE_THREAD, new ThreadScope()));
        return configurer;
    }
}
