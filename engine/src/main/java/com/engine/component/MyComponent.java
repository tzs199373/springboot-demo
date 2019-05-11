package com.engine.component;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author tzs
 * @version 1.0
 * @Description
 * @since 2019/4/16
 */
@Component
public class MyComponent {
    @Value("${engine.username}")
    private String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
