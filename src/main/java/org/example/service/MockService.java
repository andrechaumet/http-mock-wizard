package org.example.service;

import org.example.model.HttpRequest;
import org.example.model.HttpResponse;

public interface MockService {
    HttpResponse mock(final String path, final HttpRequest request);
}
