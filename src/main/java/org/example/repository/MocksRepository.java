package org.example.repository;

import org.example.model.MockFile;

import java.util.Optional;

public interface MocksRepository {
    Optional<MockFile> findByPath(final String uri);
}
