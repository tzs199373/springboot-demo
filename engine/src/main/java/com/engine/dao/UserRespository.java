package com.engine.dao;


import com.engine.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author tzs
 * @version 1.0
 * @Description
 * @since 2019/4/16
 */
public interface UserRespository  extends JpaRepository<User, Integer> {
    List<User> findUsersByUsername(String username);

}