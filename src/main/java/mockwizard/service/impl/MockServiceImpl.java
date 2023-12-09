package mockwizard.service.impl;

import mockwizard.exception.HttpMockWizardException;
import mockwizard.model.ReadOnlyMock;
import mockwizard.model.base.HttpRequest;
import mockwizard.model.base.HttpResponse;
import mockwizard.repository.MocksRepository;
import mockwizard.service.MockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

import static mockwizard.exception.HttpException.*;
import static mockwizard.service.validator.RequestValidator.*;

@Component
public class MockServiceImpl implements MockService {

    private final MocksRepository repository;

    @Autowired
    public MockServiceImpl(MocksRepository mocksRepository) {
        this.repository = mocksRepository;
    }

    //TODO: WIP
    public HttpResponse mock(final String uri, final String method, final HttpRequest request) throws IOException {
        final ReadOnlyMock mock = repository.findByUriAndMethod(uri, method);
        failIfKeyDontMatch(request, mock.getKey());
        return mock.getValue();
    }

    //TODO: Create submethod for 3 cases, do not send httprequests, fragment 🥴
    private void failIfKeyDontMatch(final HttpRequest sent, final HttpRequest found) {
        if (!validBody(sent, found)) {
            throw new HttpMockWizardException(BODY_NOT_VALID);
        }
        if (!validHeaders(sent, found)) {
            throw new HttpMockWizardException(HEADERS_NOT_VALID);
        }
        if (!validParams(sent, found)) {
            throw new HttpMockWizardException(PARAMS_NOT_VALID);
        }
    }
}
