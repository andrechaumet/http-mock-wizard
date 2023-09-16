package org.example.mocks.repository;

import org.example.mocks.files.HttpResponse;

import java.util.Optional;

public interface MocksRepository {
    Optional<HttpResponse> findByUri(final String uri);
}
