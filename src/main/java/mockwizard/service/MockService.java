package mockwizard.service;

import mockwizard.model.HttpRequest;
import mockwizard.model.MockFile;

import java.io.IOException;

public interface MockService {
    MockFile mock(final String path, final String method, final HttpRequest request) throws IOException;
}
