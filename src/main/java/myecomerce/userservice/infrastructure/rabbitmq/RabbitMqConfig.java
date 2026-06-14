package myecomerce.userservice.infrastructure.rabbitmq;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.JacksonJsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import myecomerce.userservice.application.auditService.AuditServiceImpl;

@Configuration
@EnableRabbit
public class RabbitMqConfig {

    @Bean
    public DirectExchange auditExchange() {
        return new DirectExchange(AuditServiceImpl.AuditExchangeName);
    }

    @Bean
    Queue auditQueue() {
        return new Queue(AuditServiceImpl.AuditQueueName, true);
    }

    @Bean
    public Binding auditBinding(
            Queue auditQueue,
            DirectExchange auditExchange) {

        return BindingBuilder
                .bind(auditQueue)
                .to(auditExchange)
                .with(AuditServiceImpl.AuditQueueName);
    }

    @Bean
    public JacksonJsonMessageConverter messageConverter() {
        return new JacksonJsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(
            ConnectionFactory connectionFactory,
            JacksonJsonMessageConverter converter) {

        RabbitTemplate template = new RabbitTemplate(connectionFactory);

        template.setMessageConverter(converter);

        return template;
    }
}
