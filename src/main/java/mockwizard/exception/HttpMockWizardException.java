package mockwizard.exception;

public class HttpMockWizardException extends RuntimeException {
    private int code;
    private String message;

    public HttpMockWizardException(DetailedException detailedException) {
        this.code = detailedException.getCode();
        this.message = detailedException.getMessage();
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
