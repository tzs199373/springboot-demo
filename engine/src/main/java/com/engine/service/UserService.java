package com.engine.service;

import com.engine.dao.UserRespository;
import com.engine.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    UserRespository userDao;

    public User addUser(User user){
        User u = userDao.save(user);
        return u;
    }
}
