package org.example.mocks.repository;

import org.example.mocks.files.HttpRequest;
import org.example.mocks.files.HttpResponse;
import org.example.mocks.files.MockFile;

import java.util.Optional;

public interface MocksRepository {
    Optional<MockFile> findByPath(final String uri);
    Optional<MockFile> findByRequest(final HttpRequest httpRequest);
}
