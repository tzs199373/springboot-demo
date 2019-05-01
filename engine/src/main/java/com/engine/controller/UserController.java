package com.engine.controller;

import com.engine.model.User;
import com.engine.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;

    @RequestMapping("/addUser")
    public User addUser(@RequestBody User user){
        User u = userService.addUser(user);
        return u;
    }
}
