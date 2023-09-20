package mockwizard.service;

import mockwizard.model.Mock;

import java.io.IOException;

public interface MockService {
    Mock mock(final String path, final String method) throws IOException;
}
