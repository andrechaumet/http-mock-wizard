package mockwizard.service;

import mockwizard.model.base.HttpRequest;
import mockwizard.model.base.HttpResponse;

public interface MockService {
    HttpResponse mock(final String path, final String method, final HttpRequest request);
}
