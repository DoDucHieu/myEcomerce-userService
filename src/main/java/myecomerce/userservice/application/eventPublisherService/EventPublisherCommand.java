package myecomerce.userservice.application.eventPublisherService;

public record EventPublisherCommand<T>(
    String exchangeName,
    String queueName,
    T payload
) {
}