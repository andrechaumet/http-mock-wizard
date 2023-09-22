package mockwizard.exception;

public enum DetailedException {
    HEADERS_DONT_MATCH("TODO", 4002),
    BODIES_DONT_MATCH("TODO", 4001);

    DetailedException(String message, int code) {
        this.message = message;
        this.code = code;
    }

    private String message;
    private int code;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
