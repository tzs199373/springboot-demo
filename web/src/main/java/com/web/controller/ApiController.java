package com.web.controller;

import com.web.model.Person;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;

@RestController
@RequestMapping
//Api注解，描述信息 可通过tag进行分类
//@Api(value = "ApiController", description = "ApiController")
public class ApiController {


    @PostMapping("/addPerson")
    //方法描述
    @ApiOperation(notes = "添加人员", value = "addPerson")
    public Person addPerson(
            @ApiParam(name = "name", value = "姓名") @RequestParam("name") String name,
            @ApiParam(name = "age", value = "年龄")  @RequestParam("age") Integer age) {
        Person person = new Person();
        person.setAge(age);
        person.setName(name);

        return person;
    }
}