package mockwizard.exception;

public class MockWizardException extends RuntimeException {
    private final int code;
    private final String message;

    public MockWizardException(DetailedException detailedException) {
        this.code = detailedException.getCode();
        this.message = detailedException.getMessage();
    }

    @Override
    public synchronized Throwable fillInStackTrace() {
        return this;
    }

    public int getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
