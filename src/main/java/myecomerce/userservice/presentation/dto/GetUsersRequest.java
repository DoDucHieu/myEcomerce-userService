package myecomerce.userservice.presentation.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import myecomerce.userservice.application.userService.query.GetUsersQuery;
import myecomerce.userservice.application.userService.query.PaginationQuery;

public record GetUsersRequest(
    @NotNull(message = "Page is required")
    @Min(value = 1, message = "Page must be greater than 0")
    int page,
    @Min(value = 1, message = "Size must be greater than 0")
    @Max(value = 100, message = "Size must be less than 100")
    int size,
    String search
) {
    public PaginationQuery toPaginationQuery() {
        return new PaginationQuery(page, size, search);
    }

    public GetUsersQuery toGetUsersQuery() {
        return new GetUsersQuery(toPaginationQuery());
    }
}
