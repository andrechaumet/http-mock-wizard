package mockwizard.exception;

public class HttpMockWizardException extends RuntimeException {
    private int code;
    private String message;

    public HttpMockWizardException(HttpException httpException) {
        this.code = httpException.getCode();
        this.message = httpException.getMessage();
    }

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
}
