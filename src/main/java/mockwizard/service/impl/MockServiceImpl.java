package mockwizard.service.impl;

import mockwizard.exception.HttpMockWizardException;
import mockwizard.model.HttpRequest;
import mockwizard.model.HttpResponse;
import mockwizard.model.Mock;
import mockwizard.repository.MocksRepository;
import mockwizard.service.MockService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

import static mockwizard.exception.HttpException.*;
import static mockwizard.service.utils.RequestValidator.*;

@Component
public class MockServiceImpl implements MockService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MockServiceImpl.class);

    private final MocksRepository repository;

    @Autowired
    public MockServiceImpl(MocksRepository mocksRepository) {
        this.repository = mocksRepository;
    }

    public HttpResponse mock(final String path, final String method, final HttpRequest request) throws IOException {
        final Mock mock = repository.findByPathAndMethod(path, method);
        failIfKeysDontMatch(request, mock.getKey());
        return mock.getValue();
    }

    //TODO: Create submethod for 3 cases, do not send httprequests, fragment 🥴
    private void failIfKeysDontMatch(final HttpRequest sent, final HttpRequest found) {
        if (!validBody(sent, found)) {
            LOGGER.info("");
            throw new HttpMockWizardException(BODY_NOT_VALID);
        }
        if (!validHeaders(sent, found)) {
            LOGGER.info("");
            throw new HttpMockWizardException(HEADERS_NOT_VALID);
        }
        if (!validParams(sent, found)) {
            LOGGER.info("");
            throw new HttpMockWizardException(PARAMS_NOT_VALID);
        }
    }
}
