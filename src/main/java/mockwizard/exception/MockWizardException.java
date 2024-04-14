package mockwizard.exception;

import lombok.Getter;

@Getter
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

    @Override
    public String getMessage() {
        return message;
    }
}
