package com.pinyougou.page.service.impl;

import com.pinyougou.page.service.ItemPageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
@Component
public class PageDeleteMessageListener implements MessageListener {

    @Autowired
    private ItemPageService itemPageService;

    @Override
    public void onMessage(Message message) {
        ObjectMessage objectMessage = (ObjectMessage)message;
        try {
            Long[] ids = (Long[]) objectMessage.getObject();
            System.out.println("监听到消息："+ids);
            boolean b = itemPageService.deletePage(ids);
            System.out.println(b);

        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
