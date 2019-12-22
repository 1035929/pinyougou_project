package com.itheima.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class SpringBootStart2Controller {
    @RequestMapping("/quick")
    @ResponseBody
    public String quick2(){
        return "spring boot 启动成功2！";
    }
}
