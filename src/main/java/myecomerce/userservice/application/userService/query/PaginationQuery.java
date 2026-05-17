package myecomerce.userservice.application.userService.query;

public record PaginationQuery (
    int page,
    int size,
    String search
){}
