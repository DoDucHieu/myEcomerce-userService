package myecomerce.userservice.application.authService.exception;

public class InvalidTokenException extends RuntimeException{
    public InvalidTokenException (){
        super("Invalid token");
    }
}
