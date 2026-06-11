package myecomerce.userservice.application.eventPublisherService;

public interface EventPublisherService{
    <T> void publish(EventPublisherCommand<T> command);
}
