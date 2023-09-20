package mockwizard.service.impl;

import mockwizard.model.HttpRequest;
import mockwizard.model.MockFile;
import mockwizard.repository.MocksRepository;
import mockwizard.repository.impl.MocksTxtFilesRepository;
import mockwizard.service.MockService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class MockServiceImpl implements MockService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MockServiceImpl.class);

    private final MocksRepository repository = new MocksTxtFilesRepository();

    private final RequestValidator validator = new RequestValidator();

    public MockFile mock(final String path, final String method, final HttpRequest request) throws IOException {
        final MockFile mockFile = repository.findByPathAndMethod(path, method);



        return mockFile;
    }
}
