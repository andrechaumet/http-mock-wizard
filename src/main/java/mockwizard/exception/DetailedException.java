package mockwizard.exception;

import java.time.LocalDateTime;

public enum DetailedException {
    //TODO:
    BODY_DONT_MATCH("TODO", 4001),
    HEADERS_DONT_MATCH("TODO", 4002),
    PARAMS_DONT_MATCH("TODO", 4003),
    MOCK_NOT_FOUND("TODO", 4004),
    INVALID_BODY("TODO", 4005),
    GENERIC_ERROR("TODO", 5000);

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
