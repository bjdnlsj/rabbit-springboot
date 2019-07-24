package com.bjdnlsj.rabbitspringboot;

import com.bjdnlsj.rabbitspringboot.producer.RabbitSender;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RabbitspringbootApplicationTests {

    @Test
    public void contextLoads() {

    }

    @Autowired
    private RabbitSender rabbitSender;

    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

    @Test
    public void testSender1() {
        Map<String, Object> properties = new HashMap<>();
        properties.put("number", 12345);
        properties.put("send_time", simpleDateFormat.format(new Date()));
        try {
            rabbitSender.send("Hello RabbitMQ For Spring Boot!", properties);
            Thread.sleep(2000);  // 在测试方法中进行测试，当测试方法结束，rabbitmq相关的资源也就关闭了，虽然我们的消息发送出去，但异步的ConfirmCallback却由于资源关闭而出现了上面的问题
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
