package com.web.stater;

import com.example.helloworld.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("starter")
public class MystarterApplicationTests {

    @Autowired
    private PersonService personService;

    @RequestMapping("hello")
    public void testHelloWorld() {
        personService.sayHello();
    }
}

