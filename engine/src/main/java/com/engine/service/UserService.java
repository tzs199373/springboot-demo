package com.engine.service;

import com.engine.dao.UserRespository;
import com.engine.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    UserRespository userDao;

    public User addUser(User user){
        User u = userDao.save(user);
        return u;
    }

    public User findUsersByUsername(String username){
        return userDao.findUsersByUsername(username).get(0);
    }

    public Object qryUsersByUsername(String username){
        return userDao.qryUsersByUsername(username).get(0);
    }
}
