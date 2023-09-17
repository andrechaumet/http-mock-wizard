package org.example.service.impl;

import org.example.model.HttpRequest;
import org.example.model.HttpResponse;
import org.example.model.MockFile;
import org.example.repository.MocksRepository;
import org.example.repository.impl.MocksTestRepository;
import org.example.service.MockService;

import java.util.Optional;

public class MockServiceImpl implements MockService {

    private final MocksRepository repository = new MocksTestRepository();

    public HttpResponse mock(final String path, final HttpRequest request) {
        final Optional<MockFile> mockFileOptional = repository.findByPath(path);
        if (mockFileOptional.isPresent()) {
            System.out.println("MOCK FILE FOUND");
        }
        return mockFileOptional.map(MockFile::getValue).orElse(null);
    }
}
