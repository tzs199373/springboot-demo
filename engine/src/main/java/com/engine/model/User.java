package com.engine.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author tzs
 * @version 1.0
 * @Description
 * @since 2019/4/16
 */
@javax.persistence.Entity
public class User {
    private final Logger logger = LoggerFactory.getLogger(User.class);

    private Integer id;
    private String username;

    @javax.persistence.Id
    @javax.persistence.Column(name = "id", nullable = false)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @javax.persistence.Basic
    @javax.persistence.Column(name = "username", nullable = true, length = 10)
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (id != user.id) return false;
        if (username != null ? !username.equals(user.username) : user.username != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (username != null ? username.hashCode() : 0);
        return result;
    }
}
