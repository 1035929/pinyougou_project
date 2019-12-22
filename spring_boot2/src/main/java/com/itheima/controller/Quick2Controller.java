package com.itheima.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class Quick2Controller {

    @Value("${name}")
    private String name;

    @Value("${person.address}")
    private String address;

    @RequestMapping("quick2")
    @ResponseBody
    public String quick(){
        return "name:"+name+",address:"+address;
    }
}
