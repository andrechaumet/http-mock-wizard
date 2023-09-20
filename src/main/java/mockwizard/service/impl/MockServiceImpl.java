package mockwizard.service.impl;

import mockwizard.model.HttpRequest;
import mockwizard.model.HttpResponse;
import mockwizard.model.MockFile;
import mockwizard.repository.MocksRepository;
import mockwizard.service.MockService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class MockServiceImpl implements MockService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MockServiceImpl.class);

    private final MocksRepository repository;

    private final RequestValidator validator = new RequestValidator();

    @Autowired
    public MockServiceImpl(MocksRepository mocksRepository) {
        this.repository = mocksRepository;
    }

    public HttpResponse mock(final String path, final String method, final HttpRequest request) throws IOException {
        final MockFile mockFile = repository.findByPathAndMethod(path, method);

        failIfKeysDontMatch(request, mockFile.getKey());

        if (!validator.isValid(request, mockFile.getKey())) {
            LOGGER.info("HTTP Request does not achieve required parameters.");
            throw new IllegalArgumentException();
        }

        return mockFile.getValue();
    }

    private void failIfKeysDontMatch(HttpRequest sent, HttpRequest found) {

    }
}
