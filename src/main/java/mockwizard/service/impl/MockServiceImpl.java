package mockwizard.service.impl;

import mockwizard.model.HttpRequest;
import mockwizard.model.MockFile;
import mockwizard.model.HttpResponse;
import mockwizard.repository.MocksRepository;
import mockwizard.repository.impl.MocksTxtFilesRepository;
import mockwizard.service.MockService;

import java.io.IOException;
import java.util.Optional;

public class MockServiceImpl implements MockService {

    private final MocksRepository repository = new MocksTxtFilesRepository();

    public HttpResponse mock(final String path, final HttpRequest request, final String method) throws IOException {
        final Optional<MockFile> mockFileOptional = repository.findByPathAndMethod(path, method);
        if (mockFileOptional.isPresent()) {
            System.out.println("MOCK FILE FOUND");
        }
        return mockFileOptional
                .map(MockFile::getValue)
                .orElse(null);
    }
}
