package cn.itcast.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;


@RestController
public class QueueController {
    @Autowired
    private JmsMessagingTemplate jmsMessagingTemplate;

    @RequestMapping("send")
    public void send(String text){
        jmsMessagingTemplate.convertAndSend("itcast",text);
    }

    @RequestMapping("/sendMap")
    public void sendMap(){
        // map.get("sign_name"),
        // map.get("template_code"),
        // map.get("template_param")
        Map map = new HashMap<>();
        map.put("mobile","15972214406");
        map.put("sign_name","谷鹏彪");
        map.put("template_code","SMS_125025158");
        map.put("template_param","{\"name\":\"顾与南歌\"}");
        jmsMessagingTemplate.convertAndSend("sms",map);
    }
}
