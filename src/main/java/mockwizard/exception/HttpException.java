package mockwizard.exception;

public enum HttpException {
    BODY_NOT_VALID("TODO", 4001),
    HEADERS_NOT_VALID("TODO", 4002),
    PARAMS_NOT_VALID("TODO", 4002),
    URI_NOT_FOUND("TODO", 4002);

    HttpException(String message, int code) {
        this.message = message;
        this.code = code;
    }

    private String message;
    private int code;

    public String getMessage() {
        return message;
    }

    public int getCode() {
        return code;
    }

}
