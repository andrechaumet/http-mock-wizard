package mockwizard.service;

import mockwizard.model.MockFile;

import java.io.IOException;

public interface MockService {
    MockFile mock(final String path, final String method) throws IOException;
}
