package myecomerce.userservice.infrastructure.rabbitmq;

import org.springframework.amqp.rabbit.core.RabbitTemplate;

import myecomerce.userservice.application.eventPublisherService.EventPublisherCommand;
import myecomerce.userservice.application.eventPublisherService.EventPublisherService;

public class RabbitMqPublisher implements EventPublisherService {
    private final RabbitTemplate rabbitTemplate;

    public RabbitMqPublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public <T> void publish(EventPublisherCommand<T> command) {
        String queueName = command.queueName();
        rabbitTemplate.convertAndSend(queueName, command.payload());
    }
}
