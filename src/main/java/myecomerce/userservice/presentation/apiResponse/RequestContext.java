package myecomerce.userservice.presentation.apiResponse;

public class RequestContext {
    private static final ThreadLocal<String> requestId = new ThreadLocal<>();
    
    public static void setRequestId(String requestId) {
        RequestContext.requestId.set(requestId);
    }

    public static String getRequestId() {
        return requestId.get();
    }

    public static void clearRequestId() {
        requestId.remove();
    }
}
