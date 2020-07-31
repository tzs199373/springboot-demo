package com.engine.controller;

import com.engine.model.User;
import com.engine.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;

    private final Logger logger = LoggerFactory.getLogger(UserController.class);

    @RequestMapping("/addUser")
    public User addUser(@RequestBody User user) throws Exception{
        User u = userService.addUser(user);
        logger.info("==================info");
        logger.debug("==================debug");
        return u;
    }

    @RequestMapping("/addUsers")
    public void addUsers() throws Exception{
        userService.addUsers();
    }

    @RequestMapping("/findUsersByUsername")
    public User findUsersByUsername(String username){
        User u = userService.findUsersByUsername(username);

        return u;
    }

    @RequestMapping("/qryUsersByUsername")
    public Object qryUsersByUsername(String username){
        Object o = userService.qryUsersByUsername(username);
        return o;
    }
}
