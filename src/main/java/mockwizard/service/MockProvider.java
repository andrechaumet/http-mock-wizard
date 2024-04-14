package mockwizard.service;

import lombok.RequiredArgsConstructor;
import mockwizard.exception.MockWizardException;
import mockwizard.model.Mock;
import mockwizard.model.Attribute;
import mockwizard.model.HttpRequest;
import mockwizard.model.HttpResponse;
import mockwizard.persistence.MockRepository;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.concurrent.TimeUnit;

import static mockwizard.exception.DetailedException.MOCK_NOT_FOUND;
import static mockwizard.exception.DetailedException.REQUEST_BODY_MISMATCH;
import static mockwizard.exception.DetailedException.REQUEST_HEADERS_MISMATCH;
import static mockwizard.exception.DetailedException.REQUEST_PARAMS_MISMATCH;
import static mockwizard.exception.DetailedException.UNEXPECTED_ERROR;

@Component
@RequiredArgsConstructor
public class MockProvider {

    private final MockRepository repository;

    public HttpResponse mock(final String uri, final String method, final HttpRequest request) {
        return repository
                .findByUriAndMethodOrThrow(uri, method)
                .map(mock -> process(request, mock))
                .orElseThrow(() -> new MockWizardException(MOCK_NOT_FOUND));
    }

    private HttpResponse process(final HttpRequest sent, final Mock found) {
        failIfValuesDontMatch(sent, found.key());
        delay(found);
        return found.value();
    }

    private boolean validateAttributes(Set<Attribute<?>> sentAttr, Set<Attribute<?>> foundAttr) {
        return foundAttr.stream()
                .filter(Attribute::required)
                .allMatch(required -> sentAttr.stream()
                        .filter(attr -> attr.equals(required))
                        .allMatch(attr -> attr.matchesBehaviour(required)));
    }

    private void delay(final Mock mock) {
        try {
            TimeUnit.MILLISECONDS.sleep(mock.delayMillis());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new MockWizardException(UNEXPECTED_ERROR);
        }
    }

    private void failIfValuesDontMatch(final HttpRequest sent, final HttpRequest found) {
        if (!validateAttributes(sent.headers(), found.headers())) {
            throw new MockWizardException(REQUEST_HEADERS_MISMATCH);
        }
        if (validateAttributes(sent.body(), found.body())) {
            throw new MockWizardException(REQUEST_BODY_MISMATCH);
        }
        if (validateAttributes(sent.params(), found.params())) {
            throw new MockWizardException(REQUEST_PARAMS_MISMATCH);
        }
    }
}
