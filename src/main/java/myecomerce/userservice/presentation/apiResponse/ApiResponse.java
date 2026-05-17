package myecomerce.userservice.presentation.apiResponse;

import java.time.Instant;
import java.util.List;

public record ApiResponse<T> (
    boolean success,
    String code,
    String message,
    String requestId,
    String correlationId,
    T data,
    List<ErrorDetail> errors,
    ApiMeta meta
) {
    public static <T> ApiResponse<T> success(
            String message,
            String code,
            String requestId,
            String correlationId,
            T data,
            String path
    ) {
        return new ApiResponse<>(
                true,
                code,
                message,
                requestId,
                correlationId,
                data,
                null,
                new ApiMeta(
                        Instant.now().toString(),
                        path
                )
        );
    }

    public static <T> ApiResponse<T> error(
            String message,
            String code,
            String requestId,
            String correlationId,
            List<ErrorDetail> errors,
            String path
    ) {
        return new ApiResponse<>(
                false,
                code,
                message,
                requestId,
                correlationId,
                null,
                errors,
                new ApiMeta(
                        Instant.now().toString(),
                        path
                )
        );
    }
}
