package mockwizard.service.impl;

import mockwizard.model.HttpRequest;
import mockwizard.model.Mock;
import mockwizard.model.HttpResponse;
import mockwizard.repository.MocksRepository;
import mockwizard.repository.impl.MocksTxtFilesRepository;
import mockwizard.service.MockService;

import java.io.IOException;
import java.util.Optional;

public class MockServiceImpl implements MockService {

    private final MocksRepository repository = new MocksTxtFilesRepository();
    //TODO:
    private final RequestComparator requestComparator = new RequestComparator();

    public HttpResponse mock(final String path, final HttpRequest request, final String method) throws IOException {
        final Optional<Mock> mockFileOptional = repository.findByPathAndMethod(path, method);
        if (mockFileOptional.isPresent()) {
            System.out.println("MOCK FILE FOUND");
        }
        return mockFileOptional
                .filter(found -> requestComparator.compare(request, found.getKey()))
                .map(Mock::getValue)
                .orElse(null);
    }
}
