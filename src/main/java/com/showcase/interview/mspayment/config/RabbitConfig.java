package com.showcase.interview.mspayment.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;



@Configuration
public class RabbitConfig{
	
	public static String queueNameOrder = "ms-order-queue";
	public static String queueNamePayment = "ms-payment-queue";
    
	@Bean
	Queue queueOrder() {
		return new Queue(queueNameOrder, false);
	}
	
	@Bean
	Queue queuePayment() {
		return new Queue(queueNamePayment, false);
	}
	
    @Bean
    public Jackson2JsonMessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
    
    @Bean
    public TopicExchange topicExchangeOrder() {
        return new TopicExchange("confirm-payment");
    }
    
    @Bean
    public TopicExchange topicExchangePayment() {
        return new TopicExchange("create-payment");
    }

    @Bean
    public Binding bindingOrder() {
        return BindingBuilder.bind(queueOrder()).to(topicExchangeOrder()).with("update");
    }
    
    @Bean
    public Binding bindingPayment() {
        return BindingBuilder.bind(queuePayment()).to(topicExchangePayment()).with("create");
    }
}
