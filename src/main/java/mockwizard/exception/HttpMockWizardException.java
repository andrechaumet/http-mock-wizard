package mockwizard.exception;

public class HttpMockWizardException extends RuntimeException {
    private final int code;
    private final String message;

    public HttpMockWizardException(HttpException httpException) {
        this.code = httpException.getCode();
        this.message = httpException.getMessage();
    }

    public int getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }

}
