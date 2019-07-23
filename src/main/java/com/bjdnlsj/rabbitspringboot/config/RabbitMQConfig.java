package com.bjdnlsj.rabbitspringboot.config;


import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan({"com.bjdnlsj.**.*"})
public class RabbitMQConfig {

	@Bean
	public ConnectionFactory connectionFactory(){
		CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
		connectionFactory.setAddresses("www.bjdnlsj.com:5672");
		connectionFactory.setUsername("admin");
		connectionFactory.setPassword("admin");
		connectionFactory.setVirtualHost("/");
		return connectionFactory;
	}
	
	@Bean
	public RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory) {
		RabbitAdmin rabbitAdmin = new RabbitAdmin(connectionFactory);
		rabbitAdmin.setAutoStartup(true);
		return rabbitAdmin;
	}

	@Bean
	public TopicExchange exchange001(){
		return new TopicExchange("topic001",true,false);
	}

	@Bean
	public Queue queue001() {
		return new Queue("queue001", true); //队列持久
	}

	@Bean
	Binding binding001(){
		return BindingBuilder.bind(queue001()).to(exchange001()).with("spring.*");
	}

	@Bean
	public TopicExchange exchange002() {
		return new TopicExchange("topic002", true, false);
	}

	@Bean
	public Queue queue002() {
		return new Queue("queue002", true); //队列持久
	}

	@Bean
	public Binding binding002() {
		return BindingBuilder.bind(queue002()).to(exchange002()).with("rabbit.*");
	}

	@Bean
	public Queue queue003() {
		return new Queue("queue003", true); //队列持久
	}

	@Bean
	public Binding binding003() {
		return BindingBuilder.bind(queue003()).to(exchange001()).with("mq.*");
	}

	@Bean
	public Queue queue_image() {
		return new Queue("image_queue", true); //队列持久
	}

	@Bean
	public Queue queue_pdf() {
		return new Queue("pdf_queue", true); //队列持久
	}

	@Bean
	public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory){
		RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
		return rabbitTemplate;
	}


}
