package myecomerce.userservice.application.authService.exception;

public class UnauthorizedException extends RuntimeException{
    public UnauthorizedException (){
        super("Unauthorized");
    }
}