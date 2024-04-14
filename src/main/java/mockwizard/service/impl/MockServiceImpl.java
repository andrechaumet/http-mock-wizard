package mockwizard.service.impl;

import mockwizard.exception.MockWizardException;
import mockwizard.model.Mock;
import mockwizard.model.component.Attribute;
import mockwizard.model.component.HttpRequest;
import mockwizard.model.component.HttpResponse;
import mockwizard.persistence.MockRepository;
import mockwizard.service.MockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

import static mockwizard.exception.DetailedException.MOCK_NOT_FOUND;
import static mockwizard.exception.DetailedException.REQUEST_BODY_MISMATCH;
import static mockwizard.exception.DetailedException.REQUEST_HEADERS_MISMATCH;
import static mockwizard.exception.DetailedException.REQUEST_PARAMS_MISMATCH;
import static mockwizard.exception.DetailedException.UNEXPECTED_ERROR;

@Component
public class MockServiceImpl implements MockService {

    private final MockRepository repository;

    @Autowired
    public MockServiceImpl(final MockRepository mockRepository) {
        this.repository = mockRepository;
    }

    public HttpResponse mock(final String uri, final String method, final HttpRequest request) {
        return repository
                .findByUriAndMethodOrThrow(uri, method)
                .map(mock -> process(request, mock))
                .orElseThrow(() -> new MockWizardException(MOCK_NOT_FOUND));
    }

    private HttpResponse process(final HttpRequest sent, final Mock found) {
        failIfValuesDontMatch(sent, found.getKey());
        delay(found);
        return found.getValue();
    }

    private boolean validHeaders(final HttpRequest sent, final HttpRequest found) {
        return found.headers().stream()
                .filter(Attribute::required)
                .allMatch(header -> sent.headers().contains(header));
    }

    private boolean validBody(final HttpRequest sent, final HttpRequest found) {
        return found.body().stream()
                .filter(Attribute::required)
                .allMatch(attribute -> sent.body().contains(attribute));
    }

    private boolean validParams(final HttpRequest sent, final HttpRequest found) {
        return found.params().stream()
                .filter(Attribute::required)
                .allMatch(requiredParam -> sent.params().stream()
                        .filter(sentParam -> requiredParam.equals(sentParam))
                        .anyMatch(sentParam -> requiredParam.matchesBehaviour((Attribute<?>) sentParam.value()))
                );
    }

    private void delay(final Mock mock) {
        try {
            TimeUnit.MILLISECONDS.sleep(mock.getDelayMillis());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new MockWizardException(UNEXPECTED_ERROR);
        }
    }

    private void failIfValuesDontMatch(final HttpRequest sent, final HttpRequest found) {
        if (!validHeaders(sent, found)) {
            throw new MockWizardException(REQUEST_HEADERS_MISMATCH);
        }
        if (!validBody(sent, found)) {
            throw new MockWizardException(REQUEST_BODY_MISMATCH);
        }
        if (!validParams(sent, found)) {
            throw new MockWizardException(REQUEST_PARAMS_MISMATCH);
        }
    }
}
