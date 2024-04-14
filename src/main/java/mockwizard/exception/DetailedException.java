package mockwizard.exception;

import java.time.LocalDateTime;

public enum DetailedException {
    REQUEST_BODY_MISMATCH("Request body does not match the expected format", 4001),
    REQUEST_HEADERS_MISMATCH("Request headers do not match the expected format", 4002),
    REQUEST_PARAMS_MISMATCH("Request parameters do not match the expected format", 4003),
    MOCK_NOT_FOUND("Mock data not found for the request", 4004),
    INVALID_REQUEST_BODY("Invalid request body format", 4005),
    UNEXPECTED_ERROR("An unexpected error occurred", 5000);

    DetailedException(String message, int code) {
        this.message = message;
        this.code = code;
        this.time = LocalDateTime.now();
    }

    private final LocalDateTime time;
    private final String message;
    private final int code;

    public String getMessage() {
        return message;
    }

    public int getCode() {
        return code;
    }

    public LocalDateTime getTime() {
        return time;
    }
}
