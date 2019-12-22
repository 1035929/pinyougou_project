package cn.itcast.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWordController {

    @Autowired
    private Environment env;

    @RequestMapping("/info")
    public String demo(){
        return "spring boot!"+env.getProperty("url");
    }

    @RequestMapping("/info2")
    public String demo2(){
        return "spring boot2222!"+env.getProperty("url");
    }
}
