package com.bjdnlsj.rabbitspringboot.config;


import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan({"com.bjdnlsj.**.*"})
public class RabbitMQConfig {


}
