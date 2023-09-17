package org.example.service.impl;

import org.example.mocks.files.HttpRequest;
import org.example.mocks.files.HttpResponse;
import org.example.mocks.files.MockFile;
import org.example.mocks.repository.MocksRepository;
import org.example.mocks.repository.impl.MocksTestRepository;

import java.util.Optional;

public class MockServiceImpl {

    private final MocksRepository repository = new MocksTestRepository();

    public HttpResponse test(HttpRequest request) {
        final Optional<MockFile> mockFileOptional = repository.findByPath(request.getPath());
        if (mockFileOptional.isPresent()) {
            request.toString();
            System.out.println("---");
            System.out.println(mockFileOptional.get().getKey().getPath());
        }
        return mockFileOptional.map(MockFile::getValue).orElse(null);
    }
}
