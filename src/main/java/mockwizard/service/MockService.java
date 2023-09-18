package mockwizard.service;

import mockwizard.model.HttpRequest;
import mockwizard.model.HttpResponse;

import java.io.IOException;

public interface MockService {
    HttpResponse mock(final String path, final HttpRequest request, final String method) throws IOException;
}
