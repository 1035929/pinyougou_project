package cn.itcast.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class SpringBootStartController {

    @RequestMapping("/quick")
    @ResponseBody
    public String quick(){
        return "spring boot 访问成功!";
    }
}
