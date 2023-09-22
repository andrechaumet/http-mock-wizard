package mockwizard.exception;

public class MockingException extends RuntimeException {
    private int code;
    private String message;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public MockingException(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
