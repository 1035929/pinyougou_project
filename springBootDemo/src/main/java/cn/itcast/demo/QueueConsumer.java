package cn.itcast.demo;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class QueueConsumer {
    @JmsListener(destination = "itcast")
    public void test(String text){
        System.out.println("监听到消息："+text);
    }

    @JmsListener(destination = "itcast_map")
    public void readMap(Map map){
        System.out.println(map);
    }
}
