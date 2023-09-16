package org.example.mocks.repository.impl;

import org.example.mocks.files.HttpResponse;
import org.example.mocks.repository.MocksRepository;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MocksFilesRepositoryImpl implements MocksRepository {

    private static final String BASE_PATH = "C:/Users/Andy/Desktop/test/active-mocks";

    @Override
    public Optional<HttpResponse> findByUri(String uri) {
        try {
            BufferedReader reader = Files.newBufferedReader(Paths.get(BASE_PATH + uri + "/200.txt/"));
            reader.lines().collect(Collectors.joining());
            return Optional.of()
        } catch (IOException e) {
            return Optional.empty();
        }
    }

    private static String activeMocksHandler(final String uri) throws IOException {

    }

    private static String extractBody(InputStream body) throws IOException {
        StringBuilder requestBodyBuilder = new StringBuilder();
        int bytesRead;
        byte[] buffer = new byte[1024];
        while ((bytesRead = body.read(buffer)) != -1) {
            requestBodyBuilder.append(new String(buffer, 0, bytesRead));
        }
        return requestBodyBuilder.toString();
    }

    private HttpResponse buildResponse(Stream<String>) {

    }
}
