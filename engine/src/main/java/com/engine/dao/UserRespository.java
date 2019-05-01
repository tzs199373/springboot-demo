package com.engine.dao;


import com.engine.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author tzs
 * @version 1.0
 * @Description
 * @since 2019/4/16
 */
public interface UserRespository  extends JpaRepository<User, Integer> {

}