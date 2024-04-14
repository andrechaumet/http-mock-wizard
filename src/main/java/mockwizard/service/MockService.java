package mockwizard.service;

import mockwizard.model.component.HttpRequest;
import mockwizard.model.component.HttpResponse;

public interface MockService {
    HttpResponse mock(final String path, final String method, final HttpRequest request);
}
