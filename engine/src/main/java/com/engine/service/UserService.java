package com.engine.service;

import com.commonutils.util.json.JSONArray;
import com.commonutils.util.json.JSONObject;
import com.engine.dao.UserRespository;
import com.engine.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionSynchronizationAdapter;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class UserService {
    @Autowired
    UserRespository userDao;

    @Transactional
    public User addUser(User user) throws Exception{
        User u = userDao.save(user);

        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronizationAdapter() {
            @Override
            public void afterCommit() {
                CompletableFuture.runAsync(() -> {
                    User dbu = findUsersByUsername(user.getUsername());
                    System.out.println("========"+dbu.getId());//新增用户提交后，打印出新用户的id
                });
            }
        });

        return u;
    }

    public void addUsers() throws Exception{
        List<User> users = new ArrayList<>();
        User user = new User();
        user.setId(100);
        user.setUsername("hh");

        User user2 = new User();
        user2.setId(null);
        user2.setUsername("hh2");

        users.add(user);
        users.add(user2);


        System.out.println(JSONArray.fromObject(users).toString());
        List<User> users2 = userDao.saveAll(users);
        System.out.println(JSONArray.fromObject(users2).toString());
    }

    public User findUsersByUsername(String username){
        return userDao.findUsersByUsername(username).get(0);
    }

    public Object qryUsersByUsername(String username){
        return userDao.qryUsersByUsername(username).get(0);
    }
}
