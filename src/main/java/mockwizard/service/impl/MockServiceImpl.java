package mockwizard.service.impl;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import mockwizard.exception.MockWizardException;
import mockwizard.model.ReadOnlyMock;
import mockwizard.model.base.HttpRequest;
import mockwizard.model.base.HttpResponse;
import mockwizard.model.component.Header;
import mockwizard.model.component.Param;
import mockwizard.persistence.MockRepository;
import mockwizard.service.MockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

import static java.lang.Thread.sleep;
import static mockwizard.exception.DetailedException.*;

@Component
public class MockServiceImpl implements MockService {

    private final JsonParser parser;
    private final MockRepository repository;

    @Autowired
    public MockServiceImpl(MockRepository mockRepository) {
        this.repository = mockRepository;
        this.parser = new JsonParser();
    }

    public HttpResponse mock(final String uri, final String method, final HttpRequest request) {
        return repository
                .findByUriAndMethod(uri, method)
                .map(mock -> process(request, mock))
                .orElseThrow(() -> new MockWizardException(MOCK_NOT_FOUND));
    }

    private HttpResponse process(HttpRequest sent, ReadOnlyMock found) {
        failIfValuesDontMatch(sent, found.getKey());
        delay(found);
        return found.getValue();
    }

    private boolean validHeaders(final HttpRequest sent, final HttpRequest found) {
        final List<Header> headers = found.getHeaders();
        if (headers == null) return true;
        return headers.stream()
                .filter(Header::isRequired)
                .allMatch(header -> sent.getHeaders().contains(header));
    }

    private boolean validBody(final HttpRequest sent, final HttpRequest found) {
        if (found.getBody() == null || !found.getBody().isRequired()) return true;
        final JsonElement sentBody = parser.parse(sent.getBody().getValue());
        final JsonElement foundBody = parser.parse(found.getBody().getValue());
        return sentBody.equals(foundBody);
    }

    private boolean validParams(final HttpRequest sent, final HttpRequest found) {
        final List<Param> params = found.getParams();
        if (params == null) return true;
        return params.stream()
                .filter(Param::isRequired)
                .allMatch(param -> sent.getParams().contains(param));
    }

    private void delay(ReadOnlyMock mock) {
        try {
            sleep(mock.getDelayMillis());
        } catch (InterruptedException e) {
            throw new MockWizardException(GENERIC_ERROR);
        }
    }

    private void failIfValuesDontMatch(final HttpRequest sent, final HttpRequest found) {
        if (!validHeaders(sent, found)) {
            throw new MockWizardException(HEADERS_DONT_MATCH);
        }
        if (!validBody(sent, found)) {
            throw new MockWizardException(BODY_DONT_MATCH);
        }
        if (!validParams(sent, found)) {
            throw new MockWizardException(PARAMS_DONT_MATCH);
        }
    }
}
