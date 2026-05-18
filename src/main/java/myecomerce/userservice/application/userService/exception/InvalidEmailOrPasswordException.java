package myecomerce.userservice.application.userService.exception;

public class InvalidEmailOrPasswordException extends RuntimeException {
    public InvalidEmailOrPasswordException() {
        super("Invalid email or password");
    }
}
