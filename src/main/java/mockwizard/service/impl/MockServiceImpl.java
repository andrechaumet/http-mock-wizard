package mockwizard.service.impl;

import mockwizard.model.Mock;
import mockwizard.repository.MocksRepository;
import mockwizard.repository.impl.MocksTxtFilesRepository;
import mockwizard.service.MockService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class MockServiceImpl implements MockService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MockServiceImpl.class);

    private final MocksRepository repository = new MocksTxtFilesRepository();
    //TODO:

    public Mock mock(final String path, final String method) throws IOException {
        final Mock mock = repository.findByPathAndMethod(path, method);
        return mock;
    }
}
