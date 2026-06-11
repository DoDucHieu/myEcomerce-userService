package myecomerce.userservice.application.eventPublisherService;

public record EventPublisherCommand<T>(
    String queueName,
    T payload
) {
}