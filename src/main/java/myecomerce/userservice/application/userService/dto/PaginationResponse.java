package myecomerce.userservice.application.userService.dto;

import java.util.List;

public record PaginationResponse<T> (
    List<T> data,
    int page,
    int size,
    long total
){}
