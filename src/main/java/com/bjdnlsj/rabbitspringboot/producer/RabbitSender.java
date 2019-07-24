package com.bjdnlsj.rabbitspringboot.producer;

import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate.ConfirmCallback;
import org.springframework.amqp.rabbit.core.RabbitTemplate.ReturnCallback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class RabbitSender {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    final ConfirmCallback confirmCallback = new ConfirmCallback() {
        @Override
        public void confirm(CorrelationData correlationData, boolean ack, String cause) {
            System.err.println("correlationData:" + correlationData);
            System.err.println("ack:" + ack);
            if (!ack) {
                System.err.println("异常处理……");
                System.err.println(cause);
            }
        }
    };

    // 路由失败的监听
    final ReturnCallback returnCallback = new ReturnCallback() {

        @Override
        public void returnedMessage(org.springframework.amqp.core.Message message, int replyCode, String replyText, String exchange, String routingKey) {
            System.err.println("return exchange:" + exchange + ",routingKey:" + routingKey + ",replyText:" + replyText + ",replyCode:" + replyCode);
        }
    };


    public void send(Object message, Map<String, Object> properties) throws Exception {
        MessageHeaders messageHeaders = new MessageHeaders(properties);
        Message msg = MessageBuilder.createMessage(message, messageHeaders);
        rabbitTemplate.setConfirmCallback(confirmCallback);
        rabbitTemplate.setReturnCallback(returnCallback);
        // 发送消息
        //id + 时间戳 全局唯一
        CorrelationData correlationData = new CorrelationData("1234567890");
        rabbitTemplate.convertAndSend("exchange-1", "springboot.abc", msg, correlationData);
        rabbitTemplate.convertAndSend("exchange-1", "spring.abc", msg, correlationData);
    }
}
