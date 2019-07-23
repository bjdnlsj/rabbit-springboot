package com.bjdnlsj.rabbitspringboot.producer;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;

import java.util.Map;

public class RabbitSender {
    @Autowired
    private RabbitTemplate rabbitTemplate;


    public void send(Object message, Map<String ,Object> properties) throws Exception{
        MessageHeaders messageHeaders = new MessageHeaders(properties);
        Message msg = MessageBuilder.createMessage(message,messageHeaders);
        // 发送消息
        rabbitTemplate.convertAndSend("exchang-1", "springboot.hello",msg);
    }
}
