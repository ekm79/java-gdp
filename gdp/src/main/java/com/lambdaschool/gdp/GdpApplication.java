package com.lambdaschool.gdp;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import javax.management.Query;

@SpringBootApplication
public class GdpApplication {

    static final String QUEUE = "RabbitMessageQueue";
    static final String EXCHANGE_NAME = "GDP_App";

    public static void main(String[] args) {
        SpringApplication.run(GdpApplication.class, args);
    }

    @Bean
    public TopicExchange appExchange() {
        return new TopicExchange(EXCHANGE_NAME);
    }

    @Bean
    public Queue appQueue() {
        return new Queue(QUEUE);
    }

    @Bean
    public Binding bindQueue() {
        return BindingBuilder.bind(appQueue()).to(appExchange()).with(QUEUE);
    }

    @Bean
    public RabbitTemplate rt(ConnectionFactory cf) {
        final RabbitTemplate rt = new RabbitTemplate(cf);
        rt.setMessageConverter(j2JMC());
        return rt;
    }

    @Bean
    public Jackson2JsonMessageConverter j2JMC() {
        return new Jackson2JsonMessageConverter();
    }
}
